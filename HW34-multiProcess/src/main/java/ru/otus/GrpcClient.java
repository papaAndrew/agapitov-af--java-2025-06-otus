package ru.otus;

import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.RangeMessage;
import ru.otus.protobuf.RemoteServiceGrpc;
import ru.otus.service.SeqStreamObserver;
import ru.otus.service.StoreServiceImpl;

public class GrpcClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final Logger log = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(1);
        var observer = new SeqStreamObserver(latch);

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        log.info("Channel open");
        var newStub = RemoteServiceGrpc.newStub(channel);

        var range = RangeMessage.newBuilder().setFirstValue(1).setLastValue(5).build();
        newStub.genSequence(range, observer);

        var storeService = new StoreServiceImpl(observer);
        storeService.genSequence(0, 10);

        latch.await();
        channel.shutdown();
        log.info("Channel shooting down");
    }
}
