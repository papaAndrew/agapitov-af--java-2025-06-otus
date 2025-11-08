package ru.otus.view;

import java.util.Arrays;
import java.util.stream.Collectors;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public record ClientView(String id, String name, String street, String phone) {
    public ClientView() {
        this("", "", "", "");
    }

    public ClientView(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getAddress() == null ? "" : client.getAddress().street(),
                client.getPhones() == null
                        ? ""
                        : client.getPhones().stream().map(Phone::number).collect(Collectors.joining(",")));
    }

    public Client createClient() {
        var address = street == null || street.isEmpty() ? null : new Address(null, street);
        var numberList = phone == null || phone.isEmpty()
                ? null
                : Arrays.stream(phone.split("\n")).toList();
        var phones = numberList == null
                ? null
                : numberList.stream()
                .map(item -> new Phone(null, null, numberList.indexOf(item), item))
                .toList();
        return new Client(null, name, address, phones, true);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": {id='" + id + "'; name='" + name + "'; street='" + street
                + "'; phone='" + phone + "'}";
    }
}
