package space.inevitable.eventbus.collections;

import space.inevitable.eventbus.beans.ExecutionBundle;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExecutionBundlesByTypeByInvokerName {
    private final Map<Type, ExecutionBundlesByInvokerName> executionBundlesByTypeByInvokerNameMap;

    public ExecutionBundlesByTypeByInvokerName() {
        executionBundlesByTypeByInvokerNameMap = new ConcurrentHashMap<>();
    }

    public ExecutionBundles get(final Type type, final String invokerName) {
        final boolean containsKey = containsKey(type);
        ExecutionBundlesByInvokerName executionBundlesByInvokerName;

        if (containsKey) {
            executionBundlesByInvokerName = executionBundlesByTypeByInvokerNameMap.get(type);
        } else {
            executionBundlesByInvokerName = new ExecutionBundlesByInvokerName();
            executionBundlesByTypeByInvokerNameMap.put(type, executionBundlesByInvokerName);
        }

        return executionBundlesByInvokerName.get(invokerName);
    }

    public boolean containsKey(final Type type) {
        return executionBundlesByTypeByInvokerNameMap.containsKey(type);
    }

    public boolean containsExecutionBundleFor(final Type type, final String invokerName) {
        if (!containsKey(type)) {
            return false;
        }

        final ExecutionBundlesByInvokerName executionBundlesByInvokerName = executionBundlesByTypeByInvokerNameMap.get(type);
        return executionBundlesByInvokerName.containsKey(invokerName);
    }

    public ExecutionBundlesByInvokerName getExecutionBundlesByType(final Type type) {
        return executionBundlesByTypeByInvokerNameMap.get(type);
    }

    public void putExecutionBundle(Type type, String invokerName, ExecutionBundle executionBundle) {
        final ExecutionBundles executionBundles = get(type, invokerName);
        executionBundles.add(executionBundle);
    }
}
