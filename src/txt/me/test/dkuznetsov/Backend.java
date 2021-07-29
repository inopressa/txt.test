package txt.me.test.dkuznetsov;

public class Backend implements IBackend {

    public int store(DataPacket packet) {
        System.out.println(packet.url);
        //todo do it
        return 200;
    }
}
