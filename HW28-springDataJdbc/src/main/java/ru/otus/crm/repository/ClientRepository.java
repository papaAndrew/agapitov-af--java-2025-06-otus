package ru.otus.crm.repository;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.crm.model.Client;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    //    @Override
    //    @Query(
    //            value =
    //                    """
    //            select cl.id      as client_id,
    //                   cl.name    as client_name,
    //                   adr.street as address_street,
    //                   ph.id      as phone_id,
    //                   ph.number  as phone_number,
    //            from client cl
    //                left join address adr on cl.id = adr.client_id
    //                left join phone ph on cl.id = ph.client_id
    //                            order by cl.id""",
    //            resultSetExtractorClass = ClientListResultSetExtractor.class)
    //    @Nonnull
    //    List<Client> findAll();

    Optional<Client> findByName(String name);
}
