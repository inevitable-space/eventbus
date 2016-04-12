package space.inevitable.eventbus.post

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.collections.ExecutionBundlesByTypeByInvokerName
import space.inevitable.eventbus.collections.InvokersByName
import space.inevitable.eventbus.collections.InvokersByPriority
import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.eventbus.invoke.SameThreadInvoker
import space.inevitable.eventbus.register.ListenersHostess
import spock.lang.Specification

import static org.mockito.Mockito.mock
import static space.inevitable.eventbus.ListenersStubsHolder.*

class ExecutionBundlesInPoolProxyInvokerTest extends Specification {
    private ListenersInPoolProxyInvoker listenersInPoolProxyInvoker
    private ListenerA listenerA

    def setup() {
        EventBus eventBus = mock(EventBus)

        Invoker sameThreadInvoker = new SameThreadInvoker(eventBus);

        def executionBundlesByTypeByInvokerName = new ExecutionBundlesByTypeByInvokerName()

        def invokersByName = new InvokersByName()
        invokersByName.put("SameThreadInvoker", sameThreadInvoker);

        def invokersByPriority = new InvokersByPriority()
        invokersByPriority.add(sameThreadInvoker)

        listenerA = new ListenerA()
        def listenersPoolsHostess = new ListenersHostess(executionBundlesByTypeByInvokerName, invokersByName)

        listenersPoolsHostess.register(listenerA)
        listenersInPoolProxyInvoker = new ListenersInPoolProxyInvoker(executionBundlesByTypeByInvokerName, invokersByPriority)
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
