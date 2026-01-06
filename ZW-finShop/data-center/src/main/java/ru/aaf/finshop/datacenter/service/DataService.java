package ru.aaf.finshop.datacenter.service;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.LoanClaim;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Profile getProfileByName(String name);

    Profile getProfileById(long id);

    Profile saveProfile(Profile profile);

    Client saveClient(Client client);

    Client createOrUpdateClient(Client client);

    Profile updateProfile(Profile profile);

    LoanClaim saveLoanClaim(LoanClaim loanClaim);

    List<LoanClaim> findClaimsByStatus(int status);

    Flux<LoanClaim> loadClaimsByStatus(int status);

    Mono<LoanClaim> updateClaimStatus(long claimId, int status);
}
