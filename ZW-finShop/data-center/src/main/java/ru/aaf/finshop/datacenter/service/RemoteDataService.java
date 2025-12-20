package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.proto.ClientProto;
import ru.aaf.finshop.proto.Principal;
import ru.aaf.finshop.proto.ProfileProto;
import ru.aaf.finshop.proto.RemoteServiceGrpc;

@SuppressWarnings({"java:S2094", "java:S125"})
@GrpcService
public class RemoteDataService extends RemoteServiceGrpc.RemoteServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RemoteDataService.class);

    private final DataService dataService;

    public RemoteDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void getProfileByPrincipal(Principal request, StreamObserver<ProfileProto> responseObserver) {
        log.info("getProfileByPrincipal: {}", request);
        var monoProfile = dataService.getProfileByCredential(request.getLogin());
        monoProfile.log();
        responseObserver.onNext(mapProfile(monoProfile.blockOptional().orElse(null)));
        responseObserver.onCompleted();
    }

    @Override
    public void getClientByProfile(ProfileProto request, StreamObserver<ClientProto> responseObserver) {

        var monoClient = dataService.getClientByProfileId(request.getId());
        responseObserver.onNext(mapClient(monoClient.blockOptional().orElse(null)));
        responseObserver.onCompleted();
    }

    private ProfileProto mapProfile(Profile profile) {
        if (profile == null) {
            return ProfileProto.newBuilder().setAuthorized(false).build();
        }

        return ProfileProto.newBuilder()
                .setId(profile.getProfileId())
                .setClientId(profile.getClientId() == null ? 0 : profile.getClientId())
                .setAuthorized(true)
                .build();
    }

    private ClientProto mapClient(Client client) {
        if (client == null) {
            return null;
        }
        return ClientProto.newBuilder()
                .setId(client.getClientId())
                .setName(client.getName())
                .setPassport(
                        client.getPassport() == null
                                ? null
                                : client.getPassport().serialNumber())
                .build();
    }
}
