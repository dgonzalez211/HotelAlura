package com.alura.hotel.session;
import com.alura.hotel.dto.AuthSessionDto;
import com.alura.hotel.exceptions.AuthenticationException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class SessionManagerFactory {
    private static final int MAXIMUM_SIZE = 100;
    private static final long EXPIRATION_MINUTES = 60;

    private final Cache<String, AuthSessionDto> session;

    private SessionManagerFactory() {
        session = CacheBuilder.newBuilder()
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build();
    }

    private static final class InstanceHolder {
        private static final SessionManagerFactory instance = new SessionManagerFactory();
    }

    public static SessionManagerFactory getInstance() {
        return InstanceHolder.instance;
    }

    public void createSession(AuthSessionDto authSession) {
        session.put("authSession", authSession);
    }

    public String getToken() throws AuthenticationException {
        AuthSessionDto authSessionDto = session.getIfPresent("authSession");
        if (authSessionDto == null) {
            throw new AuthenticationException("User session is null");
        }
        return authSessionDto.getToken();
    }

    public boolean isAuthenticated() throws AuthenticationException {
        AuthSessionDto authSessionDto = session.getIfPresent("authSession");
        if (authSessionDto == null) {
            throw new AuthenticationException("User session is null");
        }
        return authSessionDto.isAuthenticated();
    }

    public String getUsername() throws AuthenticationException {
        AuthSessionDto authSessionDto = session.getIfPresent("authSession");
        if (authSessionDto == null) {
            throw new AuthenticationException("User session is null");
        }
        return authSessionDto.getUsername();
    }

    public Long getUserId() throws AuthenticationException {
        AuthSessionDto authSessionDto = session.getIfPresent("authSession");
        if (authSessionDto == null) {
            throw new AuthenticationException("User session is null");
        }
        return authSessionDto.getUserId();
    }

    public void invalidateSession() {
        session.invalidateAll();
        session.cleanUp();
    }

    public void put(String key, AuthSessionDto value) {
        session.put(key, value);
    }

    public Object get(String key) {
        return session.getIfPresent(key);
    }
}
