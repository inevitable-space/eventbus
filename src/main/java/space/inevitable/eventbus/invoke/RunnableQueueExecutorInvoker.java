package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBus;
import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.thread.RunnableQueueExecutor;

import java.lang.reflect.Method;

public class RunnableQueueExecutorInvoker extends Invoker {
    private final EventBus eventBus;
    private final RunnableQueueExecutor runnableQueueExecutor;

    public RunnableQueueExecutorInvoker(final EventBus eventBus, final RunnableQueueExecutor runnableQueueExecutor) {
        this.eventBus = eventBus;
        this.runnableQueueExecutor = runnableQueueExecutor;
    }

    @Override
    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();

        final InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, eventInstance, eventBus);
        runnableQueueExecutor.add(invokerRunnable);
    }

    @Override
    public String getName() {
        return RunnableQueueExecutorInvoker.class.getSimpleName();
    }

    @Override
    public int getExecutionPriority() {
        return 0;
    }
}
