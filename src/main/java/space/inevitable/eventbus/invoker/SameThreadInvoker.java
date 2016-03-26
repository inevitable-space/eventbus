package space.inevitable.eventbus.invoker;

import space.inevitable.eventbus.beans.ExecutionBundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class SameThreadInvoker implements Invoker {
    public SameThreadInvoker() {
    }

    public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();
        //TODO : Clean V-DL
        try {
            method.invoke(listener, eventInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "SameThreadInvoker";
    }
}
