package com.ucoban.medsoft.accountserver.dao.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IKeyCloakService {
    String findUserIdByResponse(Response response);

    UsersResource getUsersResource();

    Response createUser(UserRepresentation userRepresentation);

    void resetUserPassword(UserResource userResource, CredentialRepresentation credentialRepresentation);

    ClientRepresentation getClientByClientId(String clientId);

    RealmResource getRealmByRealm();

    RolesResource rolesResourceByClientId(String clientId);

    List<String> getAvailableRoles();

    void addNewRole(String Role);
    
    void addRoleToUser(String userId, String roleName);
    
    void removeRoleFromUser(String userId, String roleName);
    
    List<String> getUserRoles(String userId);
}
