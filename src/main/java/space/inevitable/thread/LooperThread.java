package space.inevitable.thread;

public final class LooperThread extends Thread implements Looper {
    private final Runnable target;
    private volatile boolean keepLooping;
    private volatile int sleepMillisecondsBetweenLoops;

    public LooperThread(final Runnable target) {
        super(target);
        keepLooping = true;
        this.target = target;
        this.sleepMillisecondsBetweenLoops = 10;
    }

    public void breakLoop() {
        this.keepLooping = false;
    }

    @Override
    public void run() {
        while (true) {
            target.run();

            if (!keepLooping) {
                break;
            }

            try {
                Thread.sleep(getSleepMillisecondsBetweenLoops());
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public int getSleepMillisecondsBetweenLoops() {
        return sleepMillisecondsBetweenLoops;
    }

    public void setSleepMillisecondsBetweenLoops(final int sleepMillisecondsBetweenLoops) {
        this.sleepMillisecondsBetweenLoops = sleepMillisecondsBetweenLoops;
    }
}

