package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ClientRepository;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;

@SuppressWarnings({"java:S1068", "java:S125"})
@Service
public class DataServiceImpl implements DataService {

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    private final ProfileRepository profileRepository;
    private final ClientRepository clientRepository;

    public DataServiceImpl(ProfileRepository profileRepository, ClientRepository clientRepository) {
        this.profileRepository = profileRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Profile> getProfileByName(String profileName) {
        log.info("getProfileByName: {}", profileName);
        var profileFlux = profileRepository.findByName(profileName);
        return profileFlux.next().blockOptional();
    }

    @Override
    public Optional<Profile> getProfileById(long profileId) {
        log.info("getProfileById: {}", profileId);
        var profileMono = profileRepository.findById(profileId);
        return profileMono.blockOptional();
    }

    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile).block();
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client).block();
    }

    @Override
    public Client findOrCreateClient(Client client) {
        var clientId = client.getClientId();
        if (clientId == null) {
            var oldClient = clientRepository.findByName(client.getName()).blockFirst();
            if (oldClient != null) {
                clientId = oldClient.getClientId();
            }
        }
        var isNew = clientId == null;
        return clientRepository
                .save(new Client(clientId, client.getName(), client.getPassport(), isNew))
                .block();
    }

    public Profile updateProfile(Profile profile) {
        var optionalProfile = getProfileById(profile.getProfileId());
        if (optionalProfile.isEmpty()) {
            log.warn("updateProfile: not found: {}", profile);
            return profile;
        }
        var newProfile = optionalProfile.get();
        var newClient = profile.getClient() == null ? null : findOrCreateClient(profile.getClient());
        newProfile.setClient(newClient);

        return saveProfile(newProfile);
    }
}
