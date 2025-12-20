package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void getProfileByPrincipal(PrincipalProto request, StreamObserver<ProfileProto> responseObserver) {
        log.info("getProfileByPrincipal: {}", request);
        var monoProfile = dataService.getProfileByCredential(request.getLogin());
        responseObserver.onNext(mapProfile(monoProfile.blockOptional().orElse(null)));
        responseObserver.onCompleted();
    }

    @Override
    public void getClientById(IdProto request, StreamObserver<ClientProto> responseObserver) {
        log.info("getClientById: {}", request.getId());
        var monoClient = dataService.getClientById(request.getId());
        responseObserver.onNext(mapClient(monoClient.blockOptional().orElse(null)));
        responseObserver.onCompleted();
    }

    @Override
    public void saveClient(ClientProto request, StreamObserver<ClientProto> responseObserver) {
        log.info("saveClient: {}", request);
        var isNew = request.getId() == 0;
        log.info("clientId (isNew): {}({})", request.getId(), isNew);
        var monoClient =
                dataService.saveClient(new Client(isNew ? null : request.getId(), request.getName(), null, isNew));
        var client = monoClient.blockOptional().orElse(null);
        if (isNew && client != null) {
            dataService.updateProfileByClient(request.getProfileId(), client.getClientId());
        }
        responseObserver.onNext(mapClient(client));
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
                        client.getPassport() == null ? "" : client.getPassport().serialNumber())
                .build();
    }
}
