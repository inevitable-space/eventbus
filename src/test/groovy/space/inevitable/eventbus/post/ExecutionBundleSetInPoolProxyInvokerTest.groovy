package space.inevitable.eventbus.post

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.collections.ExecutionBundleSetsByType
import space.inevitable.eventbus.collections.InvokersByName
import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.eventbus.invoke.SameThreadInvoker
import space.inevitable.eventbus.register.ListenersHostess
import spock.lang.Specification

import static org.mockito.Mockito.mock
import static space.inevitable.eventbus.ListenersStubsHolder.*

class ExecutionBundleSetInPoolProxyInvokerTest extends Specification {
    private ExecutionBundleSetsByType executionBundleSetsByType
    private InvokersByName invokersByName
    private ListenersInPoolProxyInvoker listenersInPoolProxyInvoker
    private ListenerA listenerA

    def setup() {
        EventBus eventBus = mock(EventBus)

        executionBundleSetsByType = new ExecutionBundleSetsByType()
        invokersByName = new InvokersByName()

        Invoker sameThreadInvoker = new SameThreadInvoker(eventBus);
        invokersByName.put("SameThreadInvoker", sameThreadInvoker);

        listenerA = new ListenerA()
        ListenersHostess listenersPoolsHostess = new ListenersHostess(executionBundleSetsByType, invokersByName)

        listenersPoolsHostess.register(listenerA)
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundleSetsByType)
    }

    def "invokeListenersForEvent should call the methodA of the ListenerA instance when an instance of EventA is posted"() {
        when:
        listenersInPoolProxyInvoker.invokeListenersForEvent(new EventA())

        then:
        listenerA.wasMethodAInvoked()
    }

    def "invokeListenersForEvent should not call the methodA of the ListenerA instance an instance of String or EventB is posted"() {
        when:
        listenersInPoolProxyInvoker.invokeListenersForEvent("EVENT")
        listenersInPoolProxyInvoker.invokeListenersForEvent(new EventB())

        then:
        !listenerA.wasMethodAInvoked()
    }
}
