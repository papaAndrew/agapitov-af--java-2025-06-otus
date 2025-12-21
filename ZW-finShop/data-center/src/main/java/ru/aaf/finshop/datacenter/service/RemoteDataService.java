package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.proto.*;

@SuppressWarnings({"java:S2094", "java:S125"})
@GrpcService
public class RemoteDataService extends RemoteServiceGrpc.RemoteServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RemoteDataService.class);

    private final DataService dataService;

    public RemoteDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void getProfileByName(NameProto request, StreamObserver<IdProto> responseObserver) {
        log.info("getProfileByPrincipal: {}", request);
        var monoProfile = dataService.getProfileByName(request.getName());
        responseObserver.onNext(mapProfile(monoProfile));
        responseObserver.onCompleted();
    }

    @Override
    public void saveProfile(ProfileProto request, StreamObserver<ProfileProto> responseObserver) {
        log.info("saveClient: {}", request);
        var isNew = !request.getAuthorized();
        var monoProfile = dataService.saveProfile(mapProfile(request));
        log.info("clientId (isNew): {}({})", request.getId(), isNew);
        responseObserver.onNext(mapProfile(monoProfile));
        responseObserver.onCompleted();
    }

    private ProfileProto mapProfile(Mono<Profile> monoProfile) {
        var optionalProfile = monoProfile.blockOptional();
        if (optionalProfile.isEmpty()) {
            return ProfileProto.newBuilder().setAuthorized(false).build();
        }
        var profile = optionalProfile.get();
        return ProfileProto.newBuilder()
                .setId(profile.getProfileId())
                .setClient(mapClient(profile.getClient()))
                .setAuthorized(true)
                .build();
    }

    private ProfileProto.Client mapClient(Client clientProto) {
        return ProfileProto.Client.newBuilder()
                .setId(clientProto.getClientId())
                .setName(clientProto.getName())
                .setPassport(clientProto.getPassport())
                .build();
    }

    private Profile mapProfile(ProfileProto profile) {
        var isNew = profile.getId() == 0;
        return new Profile(isNew ? null : profile.getId(), profile.getName(), mapClient(profile.getClient()), isNew);
    }

    private Client mapClient(ProfileProto.Client clientProto) {
        var isNew = clientProto.getId() == 0;
        return new Client(isNew ? null : clientProto.getId(), clientProto.getName(), clientProto.getPassport(), isNew);
    }
}
