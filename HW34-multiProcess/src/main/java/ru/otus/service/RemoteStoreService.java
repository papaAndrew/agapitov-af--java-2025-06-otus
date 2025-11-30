package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.RangeMessage;
import ru.otus.protobuf.RemoteServiceGrpc;
import ru.otus.protobuf.ScoreMessage;

@SuppressWarnings("java:S2142")
public class RemoteStoreService extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void genSequence(RangeMessage request, StreamObserver<ScoreMessage> responseObserver) {

        for (var val = request.getFirstValue(); val < request.getLastValue(); val++) {
            responseObserver.onNext(scoreToMessage(val));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                responseObserver.onError(e);
            }
        }
        responseObserver.onCompleted();
    }

    private ScoreMessage scoreToMessage(long nextValue) {
        return ScoreMessage.newBuilder().setNextValue(nextValue).build();
    }
}
