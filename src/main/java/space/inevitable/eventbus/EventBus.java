package space.inevitable.eventbus;

import space.inevitable.eventbus.beans.PostOrder;
import space.inevitable.eventbus.invoke.Invoker;

public interface EventBus {
    /**
     * @param eventInstance to be passed to each method, listening to its type, with in a invocation made by an invoker
     */
    void post(final Object eventInstance);

    void post(final Object eventInstance, final PostOrder postOrder);

    /**
     * @param listener to be registered to the event bus
     */
    void register(final Object listener);

    /**
     * @param invoker to by attached to the event bus
     */
    void addInvoker(final Invoker invoker);

    /**
     * @param listener to be unregistered to the event bus
     */
    void unregister(final Object listener);
}

