package space.inevitable.eventbus.post

import space.inevitable.eventbus.invoker.Invoker
import space.inevitable.eventbus.invoker.SameThreadInvoker
import space.inevitable.eventbus.collections.ExecutionBundleSet
import space.inevitable.eventbus.subcribe.ListenersHostess
import spock.lang.Specification
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

import static space.inevitable.eventbus.ListenersStubsHolder.*

class ExecutionBundleSetInPoolProxyInvokerTest extends Specification {
    private Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap
    private Map<String, Invoker> invokersByName
    private ListenersInPoolProxyInvoker listenersInPoolProxyInvoker
    private ListenerA listenerA

    def setup() {
        executionBundleSetsByTypeMap = new ConcurrentHashMap<>()
        invokersByName = new ConcurrentHashMap<>()

        Invoker sameThreadInvoker = new SameThreadInvoker();
        invokersByName.put( "SameThreadInvoker", sameThreadInvoker );

        listenerA = new ListenerA()
        ListenersHostess listenersPoolsHostess = new ListenersHostess( executionBundleSetsByTypeMap, invokersByName )

        listenersPoolsHostess.host( listenerA )
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker( executionBundleSetsByTypeMap )
    }

    def "invokeListenersForEvent should call the methodA of the ListenerA instance when an instance of EventA is posted"() {
        when:
        listenersInPoolProxyInvoker.invokeListenersForEvent( new EventA() )

        then:
        listenerA.wasMethodAInvoked()
    }

    def "invokeListenersForEvent should not call the methodA of the ListenerA instance an instance of String or EventB is posted"() {
        when:
        listenersInPoolProxyInvoker.invokeListenersForEvent( "EVENT" )
        listenersInPoolProxyInvoker.invokeListenersForEvent( new EventB() )

        then:
        !listenerA.wasMethodAInvoked()
    }
}
