package space.inevitable.eventbus;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import space.inevitable.eventbus.invoker.Invoker;
import space.inevitable.eventbus.invoker.SameThreadInvoker;
import space.inevitable.eventbus.post.ListenersInPoolProxyInvoker;
import space.inevitable.eventbus.subcribe.ExecutionBundleSet;
import space.inevitable.eventbus.subcribe.ListenersHostess;


public final class EventBus {
    private final Map< String, Invoker > invokersByName = new ConcurrentHashMap<>();

    private final ListenersHostess listenersHostess;
    private final ListenersInPoolProxyInvoker listenersInPoolProxyInvoker;

    public EventBus() {
        final Map< Type, ExecutionBundleSet > executionBundleSetsByTypeMap = new ConcurrentHashMap<>();

        listenersHostess = new ListenersHostess( executionBundleSetsByTypeMap, invokersByName );
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker( executionBundleSetsByTypeMap );

        final Invoker sameThreadInvoker = new SameThreadInvoker();
        addInvoker( sameThreadInvoker );
    }

    public void subscribe( final Object listener ) {
        listenersHostess.host( listener );
    }

    public void post( final Object eventInstance ) {
        listenersInPoolProxyInvoker.invokeListenersForEvent( eventInstance );
    }

    public void addInvoker( final Invoker invoker ) {
        invokersByName.put( invoker.getName(), invoker );
    }
}
