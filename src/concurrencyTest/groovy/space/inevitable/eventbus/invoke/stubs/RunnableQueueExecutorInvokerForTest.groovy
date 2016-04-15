package space.inevitable.eventbus.invoke.stubs

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.RunnableQueueExecutorInvoker
import space.inevitable.thread.RunnableQueueExecutor

class RunnableQueueExecutorInvokerForTest extends RunnableQueueExecutorInvoker {
    RunnableQueueExecutorInvokerForTest(EventBus eventBus, RunnableQueueExecutor runnableQueueExecutor) {
        super(eventBus, runnableQueueExecutor)
    }

    @Override
    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        def listener = executionBundle.listener
        def increment = Counter.getAndIncrement()

        listener.orderOfInvocation = increment
        super.invoke(executionBundle, eventInstance)
    }
}
