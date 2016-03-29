package space.inevitable.eventbus;

import space.inevitable.eventbus.collections.ExecutionBundleSetsByType;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.post.ListenersInPoolProxyInvoker;
import space.inevitable.eventbus.subcribe.ListenersHostess;


public final class StandardEventBus implements EventBus {
    private final InvokersByName invokersByName;
    private final ListenersHostess listenersHostess;
    private final ListenersInPoolProxyInvoker listenersInPoolProxyInvoker;

    StandardEventBus() {
        final ExecutionBundleSetsByType executionBundleSetsByType = new ExecutionBundleSetsByType();

        invokersByName = new InvokersByName();
        listenersHostess = new ListenersHostess(executionBundleSetsByType, invokersByName);
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundleSetsByType);
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
