package txt.me.test.dkuznetsov;

public interface IBackend {
    public static final int SUCCESS = 200;
    public static final int ERROR = 404;

    int store(DataPacket packet);
}
