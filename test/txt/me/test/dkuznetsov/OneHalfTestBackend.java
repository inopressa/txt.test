package txt.me.test.dkuznetsov;

public class OneHalfTestBackend implements IBackend {
    private int index = 0;

    @Override
    public int store(DataPacket packet) {
        if (index > 0) {
            index = 0;
            return ERROR;
        }
        index++;
        return SUCCESS;
    }
}
