package space.inevitable.eventbus.register

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.ListenersStubsHolder.EventA
import space.inevitable.eventbus.ListenersStubsHolder.EventB
import space.inevitable.eventbus.ListenersStubsHolder.ListenerA
import space.inevitable.eventbus.ListenersStubsHolder.ListenerB
import space.inevitable.eventbus.collections.ExecutionBundles
import space.inevitable.eventbus.collections.ExecutionBundlesByType
import space.inevitable.eventbus.collections.InvokersByName
import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.eventbus.invoke.SameThreadInvoker
import spock.lang.Specification

import java.lang.reflect.Type

import static org.mockito.Mockito.mock

class ListenersPoolsHostessTestSet extends Specification {
    private ExecutionBundlesByType executionBundleSetsByType
    private InvokersByName invokersByName

    def setup() {
        EventBus eventBus = mock(EventBus)

        executionBundleSetsByType = new ExecutionBundlesByType();
        invokersByName = new InvokersByName()
        Invoker invoker = new SameThreadInvoker(eventBus);
        invokersByName.put(invoker.getName(), invoker)
    }

    def "Host should accommodate one object in the correct listener pool when the listenersPools is empty"() {
        given:
        ListenerA listenerA = new ListenerA()
        ListenersHostess listenersHostess = new ListenersHostess(executionBundleSetsByType, invokersByName)

        when:
        listenersHostess.register(listenerA)
        ExecutionBundles executionBundleSetForEvent = executionBundleSetsByType.get((Type) EventA.class)

        then:
        executionBundleSetForEvent != null
        executionBundleSetForEvent.size() == 1
    }

    def "Host should accommodate four objects in the correct listener pool when the listenersPools is empty"() {
        given:
        def objectA = new ListenerA()
        def objectB = new ListenerB()
        def objectC = new ListenerA()
        def objectD = new ListenerB()

        ListenersHostess listenersPoolsHostess = new ListenersHostess(executionBundleSetsByType, invokersByName)

        when:
        listenersPoolsHostess.register(objectA)
        listenersPoolsHostess.register(objectB)
        listenersPoolsHostess.register(objectC)
        listenersPoolsHostess.register(objectD)

        def executionBundleSetForEventA = executionBundleSetsByType.get((Type) EventA.class)
        def executionBundleSetForEventB = executionBundleSetsByType.get(EventB.class)

        then:
        executionBundleSetForEventA != null
        executionBundleSetForEventA.size() == 2

        executionBundleSetForEventB != null
        executionBundleSetForEventB.size() == 2
    }

    def "Host should not accommodate object if is already in the listenersPools"() {
        given:
        def objectA = new ListenerA()
        def objectB = new ListenerB()

        ListenersHostess listenersPoolsHostess = new ListenersHostess(executionBundleSetsByType, invokersByName)

        when:
        listenersPoolsHostess.register(objectA)
        listenersPoolsHostess.register(objectB)
        listenersPoolsHostess.register(objectA)
        listenersPoolsHostess.register(objectB)

        def executionBundleSetForEventA = executionBundleSetsByType.get((Type) EventA.class)
        def executionBundleSetForEventB = executionBundleSetsByType.get(EventB.class)

        then:
        executionBundleSetForEventA != null
        executionBundleSetForEventA.size() == 1

        executionBundleSetForEventB != null
        executionBundleSetForEventB.size() == 1
    }
}
