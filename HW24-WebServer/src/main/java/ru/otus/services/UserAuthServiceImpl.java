package ru.otus.services;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceClient serviceClient;

    public UserAuthServiceImpl(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        var clientList = serviceClient.findClientByName(login);
        if (clientList.isEmpty()) {
            return false;
        }
        var admin = clientList.getFirst().getUser();
        if (admin == null) {
            return false;
        }
        return password.equals(admin.getPassword());
    }
}
