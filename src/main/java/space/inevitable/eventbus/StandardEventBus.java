package space.inevitable.eventbus;

import space.inevitable.eventbus.collections.ExecutionBundleSet;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.post.ListenersInPoolProxyInvoker;
import space.inevitable.eventbus.subcribe.ListenersHostess;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class StandardEventBus implements EventBus {
    private final InvokersByName invokersByName;
    private final ListenersHostess listenersHostess;
    private final ListenersInPoolProxyInvoker listenersInPoolProxyInvoker;

    StandardEventBus() {
        final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap = new ConcurrentHashMap<>(); //TODO : hide inside a class

        invokersByName = new InvokersByName();
        listenersHostess = new ListenersHostess(executionBundleSetsByTypeMap, invokersByName);
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundleSetsByTypeMap);
    }

    public void subscribe(final Object listener) {
        listenersHostess.host(listener);
    }

    public void post(final Object eventInstance) {
        listenersInPoolProxyInvoker.invokeListenersForEvent(eventInstance);
    }

    public void addInvoker(final Invoker invoker) {
        invokersByName.put(invoker.getName(), invoker);
    }
}
