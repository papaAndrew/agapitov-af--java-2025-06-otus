package ru.otus.crm.repository;

import jakarta.annotation.Nonnull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public class ClientListResultSetExtractor implements ResultSetExtractor<List<Client>> {

    @Nonnull
    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Client> clientList = new ArrayList<>();
        List<Phone> phoneList = new ArrayList<>();
        long prevId = 0;
        while (rs.next()) {
            var clientId = rs.getLong("client_id");
            phoneList.add(new Phone(rs.getLong("phone_id"), clientId, rs.getString("phone_number")));

            if (prevId != clientId) {
                clientList.add(new Client(
                        clientId,
                        rs.getString("client_name"),
                        new Address(clientId, rs.getString("address_street")),
                        new ArrayList<>(phoneList),
                        false));
                prevId = clientId;
                phoneList.clear();
            }
        }
        return clientList;
    }
}
