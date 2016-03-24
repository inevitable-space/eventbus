package space.inevitable.eventbus.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import space.inevitable.eventbus.beans.ExecutionBundle;


public final class SameThreadInvoker implements Invoker {
    public void invoke( final ExecutionBundle executionBundle, final Object eventInstance ) {
        final Method method = executionBundle.getMethod();
        final Object listener = executionBundle.getListener();

        try {
            method.invoke( listener, eventInstance );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "SameThreadInvoker";
    }
}
