package space.inevitable.eventbus.invoke;


import space.inevitable.eventbus.beans.ExecutionBundle;

public abstract class Invoker implements Comparable<Invoker> {
    public abstract void invoke(final ExecutionBundle executionBundle, final Object eventInstance);

    public abstract String getName();

    public abstract int getExecutionPriority();

    @Override
    public int compareTo(final Invoker invoker) {
        return this.getExecutionPriority() - invoker.getExecutionPriority();
    }
}

