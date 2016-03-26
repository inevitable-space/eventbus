package space.inevitable.eventbus.collections;


import space.inevitable.eventbus.beans.ExecutionBundle;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public final class ExecutionBundleSet implements Iterable<ExecutionBundle> {
    private final Set<ExecutionBundle> executionBundlePool;

    public ExecutionBundleSet() {
        executionBundlePool = new LinkedHashSet<>();
    }

    public synchronized void add(final ExecutionBundle executionBundle) {
        executionBundlePool.add(executionBundle);
    }

    public synchronized int size() {
        return executionBundlePool.size();
    }

    @Override
    public Iterator<ExecutionBundle> iterator() {
        return executionBundlePool.iterator();
    }
}
