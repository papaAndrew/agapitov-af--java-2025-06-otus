package ru.aaf.finshop.datacenter.service;

import io.grpc.stub.StreamObserver;
import ru.aaf.finshop.datacenter.proto.ClientProto;
import ru.aaf.finshop.datacenter.proto.ProfileProto;
import ru.aaf.finshop.datacenter.proto.RemoteServiceGrpc;

@SuppressWarnings("java:S2094")
public class RemoteDataService extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void getClient(ProfileProto request, StreamObserver<ClientProto> responseObserver) {

        var client =

        responseObserver.onNext();
        responseObserver.onCompleted();
    }
}
