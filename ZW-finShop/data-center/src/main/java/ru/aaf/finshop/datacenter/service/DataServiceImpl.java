package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;

@SuppressWarnings({"java:S1068", "java:S125"})
@Service
public class DataServiceImpl implements DataService {

    private final ProfileRepository profileRepository;

    @Autowired
    public DataServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> getProfileByCredential(String credential) {
        //        return Optional.empty();
        return profileRepository.findByName(credential);
    }

    @Override
    public Client getClientByProfileId(Long profileId) {
        return null;
    }
}
