package space.inevitable.eventbus.post;

import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.collections.ExecutionBundles;
import space.inevitable.eventbus.collections.ExecutionBundlesByType;
import space.inevitable.eventbus.invoke.Invoker;

import java.lang.reflect.Type;


public final class ListenersInPoolProxyInvoker {
    private final ExecutionBundlesByType executionBundlesByType;

    public ListenersInPoolProxyInvoker(final ExecutionBundlesByType executionBundlesByType) {
        this.executionBundlesByType = executionBundlesByType;
    }

    public void invokeListenersForEvent(final Object eventInstance) {
        final ExecutionBundles listenersPool = getListenerPoolForEvent(eventInstance);

        if (listenersPool == null) {
            return;
        }

        invokeListenersInPool(listenersPool, eventInstance);
    }

    private void invokeListenersInPool(final Iterable<ExecutionBundle> executionBundles, final Object eventInstance) {
        for (final ExecutionBundle executionBundle : executionBundles) {

            final Invoker invoker = executionBundle.getInvoker();
            invoker.invoke(executionBundle, eventInstance);
        }
    }

    private ExecutionBundles getListenerPoolForEvent(final Object eventInstance) {
        final Type type = eventInstance.getClass();
        return executionBundlesByType.get(type);
    }
}
