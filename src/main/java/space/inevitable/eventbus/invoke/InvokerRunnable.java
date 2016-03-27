package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBusI;

import java.lang.reflect.Method;

class InvokerRunnable implements Runnable {
    private final Method method;
    private final Object listener;
    private final Object eventInstance;
    private final EventBusI eventBus;

    public InvokerRunnable(final Method method, final Object listener, final Object eventInstance, final EventBusI eventBus) {
        this.method = method;
        this.listener = listener;
        this.eventInstance = eventInstance;
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        try {
            method.invoke(listener, eventInstance);
        } catch (Exception e) {
            final UnhandledExceptionEvent unhandledExceptionEvent = new UnhandledExceptionEvent(e);
            eventBus.post(unhandledExceptionEvent);
        }
    }
}
