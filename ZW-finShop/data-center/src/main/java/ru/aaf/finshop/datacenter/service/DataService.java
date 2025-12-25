package ru.aaf.finshop.datacenter.service;

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
}
