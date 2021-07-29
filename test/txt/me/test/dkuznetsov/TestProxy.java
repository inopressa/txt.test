package txt.me.test.dkuznetsov;

import java.util.UUID;

public class TestProxy {

    public static void generator(IBackend backend) throws InterruptedException {
        while (true) {
            backend.store(new DataPacket(UUID.randomUUID().toString()));
            Thread.sleep(10);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Proxy proxy = new Proxy(new OneHalfTestBackend());

        proxy.start();

        generator(proxy);

//        proxy.store(new DataPacket(UUID.randomUUID().toString()));
    }
}
