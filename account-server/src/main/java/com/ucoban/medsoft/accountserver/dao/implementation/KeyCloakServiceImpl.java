package com.ucoban.medsoft.accountserver.dao.implementation;

import com.ucoban.medsoft.accountserver.dao.service.IKeyCloakService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class KeyCloakServiceImpl implements IKeyCloakService {
    private final Logger logger = LoggerFactory.getLogger(KeyCloakServiceImpl.class);
    @Value("${keycloak-admin.realm}")
    private String realm;
    @Value("${keycloak-admin.client-id}")
    private String clientId;
    private final Keycloak keycloak;

    public KeyCloakServiceImpl(@Qualifier("key-cloak-builder") Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public String findUserIdByResponse(Response response) {
        return CreatedResponseUtil.getCreatedId(response);
    }

    @Override
    public Response createUser(UserRepresentation userRepresentation) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(userRepresentation);
        logger.info("response: {}", response);
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            String errorMessage = response.readEntity(String.class);
            logger.error("Error creating user: {}", errorMessage);
        }
        return response;
    }
    
    @Override
    public void resetUserPassword(UserResource userResource, CredentialRepresentation credentialRepresentation) {
        userResource.resetPassword(credentialRepresentation);
    }

    @Override
    public ClientRepresentation getClientByClientId(String clientId) {
        return getRealmByRealm().clients().findByClientId(clientId).get(0);
    }

    @Override
    public RealmResource getRealmByRealm() {
        return keycloak.realm(realm);
    }

    @Override
    public RolesResource rolesResourceByClientId(String clientId) {
        return getRealmByRealm().clients().get(clientId).roles();
    }

    @Override
    public List<String> getAvailableRoles() {
        var clientRepresentation = getClientByClientId(clientId);
        return rolesResourceByClientId(clientRepresentation.getId())
                .list()
                .stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewRole(String roleName) {
        if (!getAvailableRoles().contains(roleName)) {
            RoleRepresentation roleRep = new RoleRepresentation();
            roleRep.setName(roleName);
            roleRep.setDescription("role_" + roleName);
            ClientRepresentation clientRep = keycloak
                    .realm(realm)
                    .clients()
                    .findByClientId(clientId)
                    .get(0);
            keycloak.realm(realm)
                    .clients()
                    .get(clientRep.getId())
                    .roles()
                    .create(roleRep);
        }
    }
    
    @Override
    public void addRoleToUser(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        UserRepresentation user = realmResource.users().get(userId).toRepresentation();
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
    }
    @Override
    public void removeRoleFromUser(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        UserRepresentation user = realmResource.users().get(userId).toRepresentation();
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().realmLevel().remove(Collections.singletonList(role));
    }
    @Override
    public List<String> getUserRoles(String userId) {
        RealmResource realmResource = keycloak.realm(realm);
        return realmResource.users().get(userId).roles().realmLevel().listAll().stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
    }
    
    @Override
    public void changePassword(String userId, String newPassword) {
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newPassword);
        credentialRepresentation.setTemporary(false);
        userResource.resetPassword(credentialRepresentation);
    }
}
