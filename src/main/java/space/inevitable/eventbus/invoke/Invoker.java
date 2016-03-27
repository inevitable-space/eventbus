package space.inevitable.eventbus.invoke;


import space.inevitable.eventbus.beans.ExecutionBundle;

public interface Invoker {
    void invoke(final ExecutionBundle executionBundle, final Object eventInstance);

    String getName();
}
