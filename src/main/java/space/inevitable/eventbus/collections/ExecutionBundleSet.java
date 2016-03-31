package space.inevitable.eventbus.collections;


import space.inevitable.eventbus.beans.ExecutionBundle;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public final class ExecutionBundleSet implements Iterable<ExecutionBundle> { //TODO : Improve the name of this class
    private final Set<ExecutionBundle> executionBundlePool; //TODO : Improve the name of this variable after improving the name of the class

    public ExecutionBundleSet() {
        executionBundlePool = new LinkedHashSet<>();
    }

    public synchronized void add(final ExecutionBundle executionBundle) {
        executionBundlePool.add(executionBundle);
    }

    public synchronized int size() {
        return executionBundlePool.size();
    }

    public synchronized void remove(final ExecutionBundle executionBundle) {
        executionBundlePool.remove(executionBundle);
    }

    @Override
    public Iterator<ExecutionBundle> iterator() {
        return executionBundlePool.iterator();
    }
}
