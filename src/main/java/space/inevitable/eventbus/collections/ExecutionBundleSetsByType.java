package space.inevitable.eventbus.collections;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExecutionBundleSetsByType {
    private final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap;

    public ExecutionBundleSetsByType() {
        executionBundleSetsByTypeMap = new ConcurrentHashMap<>();
    }


    public void put(final Type type, final ExecutionBundleSet executionBundleSet) {
        executionBundleSetsByTypeMap.put(type, executionBundleSet);
    }

    public ExecutionBundleSet get(final Type type) {
        return executionBundleSetsByTypeMap.get(type);
    }

    public boolean containsKey(final Type type) {
        return executionBundleSetsByTypeMap.containsKey(type);
    }
}
