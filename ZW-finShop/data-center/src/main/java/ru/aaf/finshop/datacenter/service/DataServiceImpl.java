package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;

@SuppressWarnings({"java:S1068", "java:S125"})
@Service
public class DataServiceImpl implements DataService {

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    //    private final ClientRepository clientRepository;
    private final ProfileRepository profileRepository;

    public DataServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Mono<Profile> getProfileByCredential(String credential) {
        log.info("getProfileByCredential: {}", credential);
        var profileFlux = profileRepository.findByName(credential);
        log.info("profileFlux: {}", profileFlux);
        return profileFlux.next();
    }

    @Override
    public Mono<Client> getClientByProfileId(Long profileId) {
        return null;
    }

    @Override
    public Mono<Profile> saveProfile(Profile profile) {
        log.info("saveProfile = {}", profile);
        return profileRepository.save(profile);
    }
}
