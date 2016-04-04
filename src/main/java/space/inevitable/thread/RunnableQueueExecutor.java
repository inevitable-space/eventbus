package space.inevitable.thread;

public interface RunnableQueueExecutor extends Looper {
    void add(Runnable runnable);
}

