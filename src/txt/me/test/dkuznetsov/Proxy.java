package txt.me.test.dkuznetsov;

import java.util.concurrent.*;

public class Proxy implements IBackend {
    private static final int PROXY_CACHE_SIZE = 10;
    private static final int PROXY_THREAD_SIZE = 10;

    private final BlockingQueue<DataPacket> cache = new ArrayBlockingQueue<>(PROXY_CACHE_SIZE);

    private final IBackend backend;
    private final ExecutorService executorService = Executors.newFixedThreadPool(PROXY_THREAD_SIZE);


    public Proxy(IBackend backend) {
        this.backend = backend;
    }

    public void start() {
        DynamicSemaphore semaphore = new DynamicSemaphore(PROXY_THREAD_SIZE);
        for (int i = 0; i < PROXY_THREAD_SIZE; i++) {
            executorService.submit(new UploadingTask(cache, backend, semaphore));
        }
    }

    @Override
    public int store(DataPacket packet) {
        try {
            System.out.println("store to proxy " + packet.url);
            cache.put(packet);
            return 200;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 404;
        }

    }
}