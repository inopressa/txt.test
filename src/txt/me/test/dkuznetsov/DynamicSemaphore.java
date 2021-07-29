package txt.me.test.dkuznetsov;

import java.util.concurrent.Semaphore;

public class DynamicSemaphore extends Semaphore {
    private final int max;

    public DynamicSemaphore(int max) {
        super(1);
        this.max = max;
    }

    public synchronized void decrease() {
        if (availablePermits() > 1) {
            reducePermits(1);
        }
    }

    public synchronized void increase() {
        int available = availablePermits();
        if (available < max) {
            release(available + 1);
        }
    }
}
