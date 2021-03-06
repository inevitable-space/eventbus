package space.inevitable.eventbus;

import space.inevitable.eventbus.beans.PostOrder;
import space.inevitable.eventbus.collections.ExecutionBundlesByTypeByInvokerName;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.collections.InvokersByPriority;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.post.ListenersInPoolProxyInvoker;
import space.inevitable.eventbus.register.ListenersHostess;


public final class StandardEventBus implements EventBus {
    private final InvokersByName invokersByName;
    private final ListenersHostess listenersHostess;
    private final ListenersInPoolProxyInvoker listenersInPoolProxyInvoker;
    private final InvokersByPriority invokersByPriority;


    public StandardEventBus() {
        final ExecutionBundlesByTypeByInvokerName executionBundlesByTypeByInvokerName = new ExecutionBundlesByTypeByInvokerName();

        invokersByPriority = new InvokersByPriority();
        invokersByName = new InvokersByName();
        listenersHostess = new ListenersHostess(executionBundlesByTypeByInvokerName, invokersByName);
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundlesByTypeByInvokerName, invokersByPriority);
    }

    /**
     * @param listener The priority of a method tagged as @Subscribe is given by the priority of
     *                 its invoker and the order in which was subscribed at the moment of registrations
     */
    @Override
    public void register(final Object listener) {
        listenersHostess.register(listener);
    }

    @Override
    public void post(final Object eventInstance) {
        listenersInPoolProxyInvoker.invokeListenersForEvent(eventInstance, PostOrder.LOWER_EXECUTION_PRIORITY_FIRST);
    }

    @Override
    public void post(final Object eventInstance, final PostOrder postOrder) {
        listenersInPoolProxyInvoker.invokeListenersForEvent(eventInstance, postOrder);
    }

    @Override
    public void addInvoker(final Invoker invoker) {
        invokersByName.put(invoker.getName(), invoker);
        invokersByPriority.add(invoker);
    }

    @Override
    public void unregister(final Object listener) {
        listenersHostess.unregister(listener);
    }
}
