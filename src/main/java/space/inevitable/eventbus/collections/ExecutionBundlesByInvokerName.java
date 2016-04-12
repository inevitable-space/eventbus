package space.inevitable.eventbus.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExecutionBundlesByInvokerName {
    private final Map<String, ExecutionBundles> executionBundlesByTypeMap;

    public ExecutionBundlesByInvokerName() {
        executionBundlesByTypeMap = new ConcurrentHashMap<>();
    }


    public void put(final String invokerName, final ExecutionBundles executionBundles) {
        executionBundlesByTypeMap.put(invokerName, executionBundles);
    }

    public ExecutionBundles get(final String invokerName) {
        final boolean containsSetForType = containsKey(invokerName);
        ExecutionBundles executionBundles;

        if (containsSetForType) {
            executionBundles = executionBundlesByTypeMap.get(invokerName);
        } else {
            executionBundles = new ExecutionBundles();
            executionBundlesByTypeMap.put(invokerName, executionBundles);
        }

        return executionBundles;
    }


    public boolean containsKey(final String invokerName) {
        return executionBundlesByTypeMap.containsKey(invokerName);
    }
}
