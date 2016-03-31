package space.inevitable.eventbus;

import space.inevitable.eventbus.invoke.Invoker;

public interface EventBus {
    /**
     * @param eventInstance to be passed to each method, listening to its type, with in a invocation made by an invoker
     */
    void post(Object eventInstance);

    /**
     * @param listener to be registered to the event bus
     */
    void register(Object listener);

    /**
     * @param invoker to by attached to the event bus
     */
    void addInvoker(Invoker invoker);

    /**
     * @param listener to be unregistered to the event bus
     */
    void unregister(Object listener);
}

