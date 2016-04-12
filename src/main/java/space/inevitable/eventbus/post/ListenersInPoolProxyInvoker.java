package space.inevitable.eventbus.post;

import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.collections.ExecutionBundles;
import space.inevitable.eventbus.collections.ExecutionBundlesByInvokerName;
import space.inevitable.eventbus.collections.ExecutionBundlesByTypeByInvokerName;
import space.inevitable.eventbus.collections.InvokersByPriority;
import space.inevitable.eventbus.invoke.Invoker;

import java.lang.reflect.Type;
import java.util.List;


public final class ListenersInPoolProxyInvoker {
    private final ExecutionBundlesByTypeByInvokerName executionBundlesByTypeByInvokerName;
    private final InvokersByPriority invokersByPriority;

    public ListenersInPoolProxyInvoker(final ExecutionBundlesByTypeByInvokerName executionBundlesByTypeByInvokerName, InvokersByPriority invokersByPriority) {
        this.executionBundlesByTypeByInvokerName = executionBundlesByTypeByInvokerName;
        this.invokersByPriority = invokersByPriority;
    }

    public void invokeListenersForEvent(final Object eventInstance) {
        final ExecutionBundlesByInvokerName executionBundlesByInvokerName = getExecutionBundlesByInvokerName(eventInstance);

        if (executionBundlesByInvokerName == null) {
            return;
        }

        invokeListenersInExecutionBundlesByInvokerName(executionBundlesByInvokerName, eventInstance);
    }

    private void invokeListenersInExecutionBundlesByInvokerName(final ExecutionBundlesByInvokerName executionBundlesByInvokerName, final Object eventInstance) {
        final List<Invoker> invokersByPriorityList = invokersByPriority.getList();

        for (final Invoker invoker : invokersByPriorityList) {
            final String invokerName = invoker.getName();
            final ExecutionBundles executionBundles = executionBundlesByInvokerName.get(invokerName);
            invokeListenersInPool(executionBundles, eventInstance);
        }
    }

    private void invokeListenersInPool(final Iterable<ExecutionBundle> executionBundles, final Object eventInstance) {
        for (final ExecutionBundle executionBundle : executionBundles) {
            final Invoker invoker = executionBundle.getInvoker();
            invoker.invoke(executionBundle, eventInstance);
        }
    }

    private ExecutionBundlesByInvokerName getExecutionBundlesByInvokerName(final Object eventInstance) {
        final Type type = eventInstance.getClass();
        return executionBundlesByTypeByInvokerName.getExecutionBundlesByType(type);
    }
}
