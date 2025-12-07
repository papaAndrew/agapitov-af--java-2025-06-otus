package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.repository.ProfileRepository;

public class DataServiceImpl implements DataService {

    private final ProfileRepository profileRepository;

    public DataServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> getProfileByCredential(String credential) {
        return profileRepository.findByName(credential);
    }

    @Override
    public Client getClientByProfileId(Long profileId) {
        return null;
    }
}
