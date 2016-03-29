package space.inevitable.eventbus.collections;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExecutionBundleSetsByType {
    private final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap;

    public ExecutionBundleSetsByType() {
        executionBundleSetsByTypeMap = new ConcurrentHashMap<>();
    }


    public void put(Type type, ExecutionBundleSet executionBundleSet) {
        executionBundleSetsByTypeMap.put(type, executionBundleSet);
    }

    public ExecutionBundleSet get(Type type) {
        return executionBundleSetsByTypeMap.get(type);
    }

    public boolean containsKey(Type type) {
        return executionBundleSetsByTypeMap.containsKey(type);
    }
}
