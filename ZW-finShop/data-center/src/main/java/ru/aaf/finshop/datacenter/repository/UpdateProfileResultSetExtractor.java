package ru.aaf.finshop.datacenter.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Profile;

public class UpdateProfileResultSetExtractor implements ResultSetExtractor<Mono<Profile>> {

    @Override
    public Mono<Profile> extractData(ResultSet rs) throws SQLException, DataAccessException {
        //        var Profile = new ArrayList<Manager>();
        //        String prevManagerId = null;
        //        while (rs.next()) {
        //            var managerId = rs.getString("manager_id");
        //            Manager manager = null;
        //            if (prevManagerId == null || !prevManagerId.equals(managerId)) {
        //                manager = new Manager(
        //                        managerId, rs.getString("manager_label"), new HashSet<>(), new ArrayList<>(), false);
        //                managerList.add(manager);
        //                prevManagerId = managerId;
        //            }
        //            Long clientId = (Long) rs.getObject("client_id");
        //            if (manager != null && clientId != null) {
        //                manager.getClients()
        //                        .add(new Client(
        //                                clientId,
        //                                rs.getString("client_name"),
        //                                managerId,
        //                                rs.getInt("order_column"),
        //                                new ClientDetails(clientId, rs.getString("client_info"))));
        //            }
        //        }
        return null;
    }
}
