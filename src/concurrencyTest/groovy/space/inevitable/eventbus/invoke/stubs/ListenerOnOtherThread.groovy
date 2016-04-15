package space.inevitable.eventbus.invoke.stubs

import space.inevitable.eventbus.Subscribe

class ListenerOnOtherThread {
    Thread thread
    boolean methodCalled
    public int orderOfInvocation = -1

    public ListenerOnOtherThread(Thread thread) {
        this.thread = thread
    }

    @Subscribe("RunnableQueueExecutorInvoker")
    public void method(EventBetweenThreads eventBetweenThreads) {
        Thread currentThread = Thread.currentThread()
        Thread threadOfCreation = eventBetweenThreads.threadOfCreation

        if (!currentThread.equals(this.thread) || threadOfCreation.equals(currentThread)) {
            throw new IllegalStateException("NOT IN THE SAME THREAD")
        }

        if (methodCalled) {
            throw new Exception("Invoked twice")
        }
        methodCalled = true
    }
}
