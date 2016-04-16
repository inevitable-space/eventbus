package space.inevitable.eventbus.invoke;

import space.inevitable.eventbus.EventBus;
import space.inevitable.eventbus.exception.InvokerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Used by all the invokers, responsible for the invocation through reflection of the subscribed methods.
 * Avoid crash except when a InvokerException is thrown.
 */
public class InvokerRunnable implements Runnable {
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
        } catch (final InvocationTargetException invocationTargetException) {
            handleInvocationTargetException(invocationTargetException);
        } catch (Exception exception) {
            handleException(exception);
        }
    }

    private void handleInvocationTargetException(final InvocationTargetException invocationTargetException) {
        final Throwable reason = invocationTargetException.getTargetException();

        if (reason instanceof InvokerException) {
            throw (InvokerException) reason;
        }

        handleException(reason);
    }

    private void handleException(final Throwable reason) {
        final UnhandledExceptionEvent unhandledExceptionEvent = new UnhandledExceptionEvent(reason);
        eventBus.post(unhandledExceptionEvent);
    }
}

