package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.LoanClaim;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ClientRepository;
import ru.aaf.finshop.datacenter.repository.LoanClaimRepository;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;

@SuppressWarnings({"java:S1068", "java:S125"})
@Service
public class DataServiceImpl implements DataService {

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    private final ProfileRepository profileRepository;
    private final ClientRepository clientRepository;
    private final LoanClaimRepository loanClaimRepository;

    public DataServiceImpl(ProfileRepository profileRepository, ClientRepository clientRepository, LoanClaimRepository loanClaimRepository) {
        this.profileRepository = profileRepository;
        this.clientRepository = clientRepository;
        this.loanClaimRepository = loanClaimRepository;
    }

    @Override
    public Profile getProfileByName(String profileName) {
        log.info("getProfileByName: {}", profileName);
        var profileFlux = profileRepository.findByName(profileName);
        return profileFlux.next().block();
    }

    @Override
    public Profile getProfileById(long profileId) {
        log.info("getProfileById: {}", profileId);
        var profileOptional = profileRepository.findById(profileId).blockOptional();
        if (profileOptional.isPresent()) {
            var profile = profileOptional.get();
            if (profile.getClientId() != null) {
                profile.setClient(getClientById(profile.getClientId()));
            }
            return profile;
        }
        return null;
    }

    public Client getClientById(long clientId) {
        log.info("getClientById: {}", clientId);
        return clientRepository.getById(clientId).block();
    }

    @Override
    public Profile saveProfile(Profile profile) {
        log.info("saveProfile: {}", profile);
        return profileRepository.save(profile).block();
    }

    @Override
    public Client saveClient(Client client) {
        log.info("saveClient: {}", client);
        return clientRepository.save(client).block();
    }

    @Override
    public Client createOrUpdateClient(Client client) {
        log.info("findOrCreateClient: {}", client);
        var clientId = client.getClientId();
        if (clientId == null) {
            var oldClient = clientRepository.findByName(client.getName()).blockFirst();
            if (oldClient != null) {
                clientId = oldClient.getClientId();
            }
        }
        var isNew = clientId == null;
        var newClient = new Client(clientId, client.getName(), client.getPassport(), isNew);
        log.info("findOrCreateClient: updating: {}", newClient);
        return clientRepository.save(newClient).block();
    }

    public Profile updateProfile(Profile byProfile) {
        log.info("updateProfile: {}", byProfile);
        var profile = getProfileById(byProfile.getProfileId());
        if (profile == null) {
            log.warn("updateProfile: not found: {}", byProfile);
            return byProfile;
        }
        var client = byProfile.getClient() != null ? createOrUpdateClient(byProfile.getClient()) : null;
        profile.client(client);

        log.info("updateProfile: updating: {}", profile);
        return saveProfile(profile);
    }

    @Override
    public LoanClaim saveLoanClaim(LoanClaim loanClaim) {
        log.info("saveLoanClaim: {}", loanClaim);
        return loanClaimRepository.save(loanClaim).block();
    }
}
