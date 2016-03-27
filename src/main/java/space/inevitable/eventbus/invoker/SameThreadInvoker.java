package space.inevitable.eventbus.invoker;

import space.inevitable.eventbus.beans.ExecutionBundle;

import java.lang.reflect.Method;


public final class SameThreadInvoker implements Invoker {
    public SameThreadInvoker() {
    }

    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();

        final InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, eventInstance);
        invokerRunnable.run();
    }

    @Override
    public String getName() {
        return "SameThreadInvoker";
    }
}
