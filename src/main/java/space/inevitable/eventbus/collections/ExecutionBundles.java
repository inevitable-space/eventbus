package space.inevitable.eventbus.collections;


import space.inevitable.eventbus.beans.ExecutionBundle;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public final class ExecutionBundles implements Iterable<ExecutionBundle> {
    private final Set<ExecutionBundle> executionBundleSet;

    public ExecutionBundles() {
        executionBundleSet = new LinkedHashSet<>();
    }

    public synchronized void add(final ExecutionBundle executionBundle) {
        executionBundleSet.add(executionBundle);
    }

    public synchronized int size() {
        return executionBundleSet.size();
    }

    public synchronized void remove(final ExecutionBundle executionBundle) {
        executionBundleSet.remove(executionBundle);
    }

    @Override
    public Iterator<ExecutionBundle> iterator() {
        return executionBundleSet.iterator();
    }
}
