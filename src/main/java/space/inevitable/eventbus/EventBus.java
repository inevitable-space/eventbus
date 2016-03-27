package space.inevitable.eventbus;

import space.inevitable.eventbus.collections.ExecutionBundleSet;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.invoke.SameThreadInvoker;
import space.inevitable.eventbus.post.ListenersInPoolProxyInvoker;
import space.inevitable.eventbus.subcribe.ListenersHostess;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class EventBus {
    private final InvokersByName invokersByName;
    private final ListenersHostess listenersHostess;
    private final ListenersInPoolProxyInvoker listenersInPoolProxyInvoker;

    public EventBus() {
        final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap = new ConcurrentHashMap<>();

        invokersByName = new InvokersByName();
        listenersHostess = new ListenersHostess(executionBundleSetsByTypeMap, invokersByName);
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundleSetsByTypeMap);

        final Invoker sameThreadInvoker = new SameThreadInvoker();
        addInvoker(sameThreadInvoker);
    }

    /**
     * @param listener to be subscribed to the event bus
     */
    public void subscribe(final Object listener) {
        listenersHostess.host(listener);
    }

    /**
     * @param eventInstance to be passed to each method, listening to its type, with in a invocation made by an invoker
     */
    public void post(final Object eventInstance) {
        listenersInPoolProxyInvoker.invokeListenersForEvent(eventInstance);
    }

    /**
     * @param invoker to by attached to the event bus
     */
    public void addInvoker(final Invoker invoker) {
        invokersByName.put(invoker.getName(), invoker);
    }
}
