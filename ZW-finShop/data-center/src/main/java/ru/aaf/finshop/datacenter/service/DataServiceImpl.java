package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;
import ru.aaf.finshop.datacenter.sessionmanager.TransactionManager;

@SuppressWarnings({"java:S1068", "java:S125"})
@Service
public class DataServiceImpl implements DataService {

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    //    private final ClientRepository clientRepository;
    private final ProfileRepository profileRepository;
    private final TransactionManager transactionManager;

    public DataServiceImpl(ProfileRepository profileRepository, TransactionManager transactionManager) {
        this.profileRepository = profileRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<Profile> getProfileByCredential(String credential) {
        log.info("getProfileByCredential: {}", credential);
        var profileFlux = profileRepository.findByName(credential);
        return profileFlux.next().blockOptional();
    }

    @Override
    public Client getClientByProfileId(Long profileId) {
        return null;
    }

    @Override
    public void saveProfile(Profile profile) {
        transactionManager.doInTransaction(() -> profileRepository.save(profile));
    }
}
