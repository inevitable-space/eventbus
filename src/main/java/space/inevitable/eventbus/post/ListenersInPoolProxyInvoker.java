package space.inevitable.eventbus.post;

import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.collections.ExecutionBundleSet;
import space.inevitable.eventbus.invoker.Invoker;

import java.lang.reflect.Type;
import java.util.Map;


public final class ListenersInPoolProxyInvoker {
    private final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap;

    public ListenersInPoolProxyInvoker(final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap) {
        this.executionBundleSetsByTypeMap = executionBundleSetsByTypeMap;
    }

    public void invokeListenersForEvent(final Object eventInstance) {
        final ExecutionBundleSet listenersPool = getListenerPoolForEvent(eventInstance);

        if (listenersPool == null) {
            return;
        }

        invokeListenersInPool(listenersPool, eventInstance);
    }

    private void invokeListenersInPool(final Iterable<ExecutionBundle> executionBundles, final Object eventInstance) {
        for (final ExecutionBundle executionBundle : executionBundles) {
            //TODO : Clean V-DL
            final Invoker invoker = executionBundle.getInvoker();
            invoker.invoke(executionBundle, eventInstance);
        }
    }

    private ExecutionBundleSet getListenerPoolForEvent(final Object eventInstance) {
        final Type type = eventInstance.getClass();
        return executionBundleSetsByTypeMap.get(type);
    }
}
