package ru.otus.services;

import java.util.Map;

public class UserAuthServiceImpl implements UserAuthService {

    Map<String, String> credentials = Map.of("admin", "admin");

    @Override
    public boolean authenticate(String login, String password) {
        var pwd = credentials.get(login);
        return pwd != null && pwd.equals(password);
    }
}
