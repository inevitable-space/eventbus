package space.inevitable.eventbus.collections;


import space.inevitable.eventbus.beans.ExecutionBundle;

import java.util.*;


public final class ExecutionBundles implements Iterable<ExecutionBundle> {
    private final Set<ExecutionBundle> executionBundlesSet;
    private final List<ExecutionBundle> executionBundlesList;

    public ExecutionBundles() {
        executionBundlesSet = new HashSet<>();
        executionBundlesList = new ArrayList<>();
    }

    public synchronized void add(final ExecutionBundle executionBundle) {
        final boolean added = executionBundlesSet.add(executionBundle);
        if (!added) {
            return;
        }
        executionBundlesList.add(executionBundle);
    }

    public synchronized int size() {
        return executionBundlesSet.size();
    }

    public synchronized void remove(final ExecutionBundle executionBundle) {
        executionBundlesSet.remove(executionBundle);
        executionBundlesList.remove(executionBundle);
    }

    public synchronized ExecutionBundle get(int index) {
        return executionBundlesList.get(index);
    }

    @Override
    public Iterator<ExecutionBundle> iterator() {
        return executionBundlesList.iterator();
    }
}
