package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBus;
import space.inevitable.eventbus.beans.ExecutionBundle;

import java.lang.reflect.Method;


public final class SameThreadInvoker implements Invoker {
    private final EventBus eventBus;

    public SameThreadInvoker(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();

        final InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, eventInstance, eventBus);
        invokerRunnable.run();
    }

    @Override
    public String getName() {
        return "SameThreadInvoker";
    }
}
