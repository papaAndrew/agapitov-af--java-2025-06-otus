package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.LoanClaim;
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
        log.info("getProfileByName: {}", request);
        var profile = dataService.getProfileByName(request.getName());
        log.info("getProfileByName: profile: {}", profile);
        responseObserver.onNext(mapProfileId(profile));
        responseObserver.onCompleted();
    }

    @Override
    public void getProfileById(IdProto request, StreamObserver<ProfileProto> responseObserver) {
        log.info("getProfileById: {}", request);
        var profile = dataService.getProfileById(request.getId());
        log.info("getProfileById: profile: {}", profile);

        responseObserver.onNext(mapProfile(profile));
        responseObserver.onCompleted();
    }

    @Override
    public void saveProfile(ProfileProto request, StreamObserver<ProfileProto> responseObserver) {
        log.info("saveProfile: {}", request);
        var profile = dataService.updateProfile(mapProfile(request));
        log.info("saveProfile: updatedProfile: {}", mapProfile(request));

        responseObserver.onNext(mapProfile(profile));
        responseObserver.onCompleted();
    }

    @Override
    public void saveClaim(LoanClaimProto request, StreamObserver<LoanClaimProto> responseObserver) {
        log.info("saveClaim: {}", request);
        var loanClaim = dataService.saveLoanClaim(mapLoanClaim(request));

        responseObserver.onNext(mapLoanClaim(loanClaim));
        responseObserver.onCompleted();
    }

    private IdProto mapProfileId(Profile profile) {
        return IdProto.newBuilder()
                .setId(profile == null ? 0 : profile.getProfileId())
                .build();
    }

    private ProfileProto mapProfile(Profile profile) {
        var builder = ProfileProto.newBuilder();
        if (profile == null) {
            builder.setAuthorized(false).clearId();
        } else {
            builder.setAuthorized(true)
                    .setId(profile.getProfileId())
                    .setName(profile.getName())
                    .setClient(mapClient(profile.getClient()));
        }
        return builder.build();
    }

    private ProfileProto.Client.Builder mapClient(Client clientProto) {
        return clientProto == null
                ? ProfileProto.Client.newBuilder().clear()
                : ProfileProto.Client.newBuilder()
                        .setId(clientProto.getClientId())
                        .setName(clientProto.getName())
                        .setPassport(clientProto.getPassport());
    }

    private Profile mapProfile(ProfileProto profile) {
        var isNew = profile.getId() == 0;
        return new Profile(isNew ? null : profile.getId(), profile.getName(), null, isNew)
                .client(mapClient(profile.getClient()));
    }

    private Client mapClient(ProfileProto.Client clientProto) {
        if (clientProto.getName().isBlank()) {
            return null;
        }
        var isNew = clientProto.getId() == 0;
        return new Client(isNew ? null : clientProto.getId(), clientProto.getName(), clientProto.getPassport(), isNew);
    }

    private LoanClaim mapLoanClaim(LoanClaimProto loanClaimProto) {
        return new LoanClaim(
                null,
                loanClaimProto.getClientId(),
                loanClaimProto.getStatus(),
                loanClaimProto.getPeriod(),
                loanClaimProto.getAmount()
        );
    }
    private LoanClaimProto mapLoanClaim(LoanClaim loanClaim) {
        return LoanClaimProto.newBuilder()
                .setId(loanClaim.id())
                .setClientId(loanClaim.clientId())
                .setStatus(loanClaim.status())
                .setPeriod(loanClaim.period())
                .setAmount(loanClaim.amount())
                .build();

    }
}
