package space.inevitable.thread;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class RunnableQueueExecutorLooperThread extends Thread implements Looper, RunnableQueueExecutor {
    final private BlockingQueue<Runnable> runnableQueue;
    private volatile boolean keepLooping;

    public RunnableQueueExecutorLooperThread() {
        super();
        runnableQueue = new LinkedTransferQueue<>();
        keepLooping = true;
    }

    @Override
    public void run() {
        while (true) {
            Runnable runnable = null;

            try {
                runnable = runnableQueue.take();
            } catch (InterruptedException ignore) {
            }

            if (runnable != null) {
                runnable.run();
            }

            if (!keepLooping) {
                return;
            }
        }
    }

    @Override
    public void breakLoop() {
        keepLooping = false;
    }

    public void add(final Runnable runnable) {
        runnableQueue.add(runnable);
    }
}


