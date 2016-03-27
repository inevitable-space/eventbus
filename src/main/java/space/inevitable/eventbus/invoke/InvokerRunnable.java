package space.inevitable.eventbus.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class InvokerRunnable implements Runnable {
    private final Method method;
    private final Object listener;
    private final Object eventInstance;

    public InvokerRunnable(final Method method, final Object listener, final Object eventInstance) {
        this.method = method;
        this.listener = listener;
        this.eventInstance = eventInstance;
    }

    @Override
    public void run() {
        try {
            method.invoke(listener, eventInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
