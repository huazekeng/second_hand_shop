package com.mrli.second_shop.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public final class SimpleGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 500L;
    private String role;

    public SimpleGrantedAuthority() {}

    public SimpleGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    @JsonIgnore
    public String getAuthority() {
        return this.role;
    }

    public String getRole() { return this.role; }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof SimpleGrantedAuthority ? this.role.equals(((SimpleGrantedAuthority)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}

