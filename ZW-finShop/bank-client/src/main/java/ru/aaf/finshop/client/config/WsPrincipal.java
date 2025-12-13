package ru.aaf.finshop.client.config;

import java.security.Principal;

public class WsPrincipal implements Principal {

    private final String name;

    public WsPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
