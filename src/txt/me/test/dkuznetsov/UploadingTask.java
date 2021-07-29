package txt.me.test.dkuznetsov;

import java.util.concurrent.BlockingQueue;

public class UploadingTask implements Runnable {
    private final BlockingQueue<DataPacket> cache;
    private final IBackend backend;
    private final DynamicSemaphore semaphore;

    public UploadingTask(BlockingQueue<DataPacket> cache, IBackend backend, DynamicSemaphore semaphore) {
        this.cache = cache;
        this.backend = backend;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                try {
                    DataPacket packet = cache.take();
                    if (backend.store(packet) != IBackend.SUCCESS) {
                        cache.put(packet);
                        Thread.sleep(10);
                        semaphore.decrease();
                    } else {
                        semaphore.increase();
                    }
                } finally {
                    semaphore.release();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
