package com.ucoban.medsoft.accountserver.entity;

import jakarta.persistence.*;


import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    public Role(ERole role) {
        this.role = role;
    }

    public Role(long id, ERole role) {
        this.id = id;
        this.role = role;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Role r && r.id == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
