package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBus;
import space.inevitable.eventbus.beans.ExecutionBundle;

import java.lang.reflect.Method;


public class SameThreadInvoker extends Invoker {
    private final EventBus eventBus;

    public SameThreadInvoker(final EventBus eventBus) {
        super();
        this.eventBus = eventBus;
    }

    @Override
    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();

        final InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, eventInstance, eventBus);
        invokerRunnable.run();
    }

    @Override
    public String getName() {
        return SameThreadInvoker.class.getSimpleName();
    }

    @Override
    public int getExecutionPriority() {
        return 1000;
    }
}
