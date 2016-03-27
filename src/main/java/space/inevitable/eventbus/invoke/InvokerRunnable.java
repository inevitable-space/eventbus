package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBus;

import java.lang.reflect.Method;

class InvokerRunnable implements Runnable {
    private final Method method;
    private final Object listener;
    private final Object eventInstance;
    private final EventBus eventBus;

    public InvokerRunnable(final Method method, final Object listener, final Object eventInstance, final EventBus eventBus) {
        this.method = method;
        this.listener = listener;
        this.eventInstance = eventInstance;
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        try {
            method.invoke(listener, eventInstance);
        } catch (Exception unhandledException) {
            final UnhandledExceptionEvent unhandledExceptionEvent = new UnhandledExceptionEvent(unhandledException);
            eventBus.post(unhandledExceptionEvent);
        }
    }
}
