package space.inevitable.thread;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class RunnableQueueExecutorLooperThread extends Thread implements RunnableQueueExecutor {
    final private BlockingQueue<Runnable> runnableQueue;
    private volatile boolean keepLooping;
    private boolean allowAddBeforeStartTheThread;

    public RunnableQueueExecutorLooperThread() {
        super();
        runnableQueue = new LinkedTransferQueue<>();
        keepLooping = true;

        setAllowAddBeforeStartTheThread(false);
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
        if (!this.allowAddBeforeStartTheThread && !this.isAlive()) {
            String message = "Cannot add runnable before invoking start, unless setAllowAddBeforeStartTheThread(true) is called";
            throw new IllegalStateException(message);
        }
        runnableQueue.add(runnable);
    }

    public void setAllowAddBeforeStartTheThread(final boolean allowAddBeforeStartTheThread) {
        this.allowAddBeforeStartTheThread = allowAddBeforeStartTheThread;
    }
}


