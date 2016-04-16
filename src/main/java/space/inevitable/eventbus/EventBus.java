package space.inevitable.eventbus;

import space.inevitable.eventbus.beans.PostOrder;
import space.inevitable.eventbus.invoke.Invoker;

public interface EventBus {
    void post(final Object eventInstance);

    void post(final Object eventInstance, final PostOrder postOrder);

    void register(final Object listener);

    void addInvoker(final Invoker invoker);

    void unregister(final Object listener);
}

