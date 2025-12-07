package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import java.util.Optional;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.proto.ClientProto;
import ru.aaf.finshop.datacenter.proto.Principal;
import ru.aaf.finshop.datacenter.proto.ProfileProto;
import ru.aaf.finshop.datacenter.proto.RemoteServiceGrpc;

@SuppressWarnings("java:S2094")
public class RemoteDataService extends RemoteServiceGrpc.RemoteServiceImplBase {

    private final DataService dataService;

    public RemoteDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void getProfileByPrincipal(Principal request, StreamObserver<ProfileProto> responseObserver) {
        var profile = dataService.getProfileByCredential(request.getLogin());
        responseObserver.onNext(mapProfile(profile));
        responseObserver.onCompleted();
    }

    @Override
    public void getClientByProfile(ProfileProto request, StreamObserver<ClientProto> responseObserver) {

        var client = dataService.getClientByProfileId(request.getId());
        responseObserver.onNext(mapClient(client));
        responseObserver.onCompleted();
    }

    private ProfileProto mapProfile(Optional<Profile> optionalProfile) {
        if (optionalProfile.isEmpty()) {
            return null;
        }
        var profile = optionalProfile.get();
        return ProfileProto.newBuilder()
                .setId(profile.id())
                .setClientId(profile.clientId())
                .setCredential(profile.credential())
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
