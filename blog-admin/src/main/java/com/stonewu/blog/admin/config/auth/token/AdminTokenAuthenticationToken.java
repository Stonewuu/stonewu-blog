package com.stonewu.blog.admin.config.auth.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AdminTokenAuthenticationToken extends AbstractAuthenticationToken {
    /**
     *
     */
    private static final long serialVersionUID = -2612247621188215419L;
    private String token;
    private String host;

    public AdminTokenAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public AdminTokenAuthenticationToken(String token, String host, boolean authenticated) {
        super(null);
        this.token = token;
        this.host = host;
        super.setAuthenticated(authenticated);
    }

    @Override
    public Object getCredentials() {
        return host;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
