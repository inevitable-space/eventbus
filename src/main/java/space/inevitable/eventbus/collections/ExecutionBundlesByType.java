package space.inevitable.eventbus.collections;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExecutionBundlesByType {
    private final Map<Type, ExecutionBundles> executionBundlesByTypeMap;

    public ExecutionBundlesByType() {
        executionBundlesByTypeMap = new ConcurrentHashMap<>();
    }


    public void put(final Type type, final ExecutionBundles executionBundles) {
        executionBundlesByTypeMap.put(type, executionBundles);
    }

    public ExecutionBundles get(final Type type) {
        final boolean containsSetForType = containsKey(type);
        ExecutionBundles executionBundles;

        if (containsSetForType) {
            executionBundles = executionBundlesByTypeMap.get(type);
        } else {
            executionBundles = new ExecutionBundles();
            executionBundlesByTypeMap.put(type, executionBundles);
        }

        return executionBundles;
    }


    public boolean containsKey(final Type type) {
        return executionBundlesByTypeMap.containsKey(type);
    }
}
