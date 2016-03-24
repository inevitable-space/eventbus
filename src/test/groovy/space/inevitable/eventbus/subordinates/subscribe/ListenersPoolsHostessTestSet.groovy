package space.inevitable.eventbus.subordinates.subscribe

import space.inevitable.eventbus.invoker.Invoker
import space.inevitable.eventbus.ListenersStubsHolder.EventA
import space.inevitable.eventbus.ListenersStubsHolder.EventB
import space.inevitable.eventbus.ListenersStubsHolder.ListenerA
import space.inevitable.eventbus.ListenersStubsHolder.ListenerB
import space.inevitable.eventbus.invoker.SameThreadInvoker
import space.inevitable.eventbus.subcribe.ExecutionBundleSet
import space.inevitable.eventbus.subcribe.ListenersHostess
import spock.lang.Specification

import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

class ListenersPoolsHostessTestSet extends Specification {
    private Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap = new ConcurrentHashMap<>()
    private Map<String, Invoker> invokersByName = new ConcurrentHashMap<>()

    def setup() {
        executionBundleSetsByTypeMap = new ConcurrentHashMap<>();
        invokersByName = new ConcurrentHashMap<>();
        Invoker invoker = new SameThreadInvoker();
        invokersByName.put( invoker.getName(), invoker )
    }

    def "Host should accommodate one object in the correct listener pool when the listenersPools is empty"() {
        given:
        ListenerA listenerA = new ListenerA()
        ListenersHostess listenersPoolsHostess = new ListenersHostess( executionBundleSetsByTypeMap, invokersByName )

        when:
        listenersPoolsHostess.host( listenerA )
        ExecutionBundleSet executionBundleSetForEvent = executionBundleSetsByTypeMap.get( ( Type ) EventA.class )

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

        ListenersHostess listenersPoolsHostess = new ListenersHostess( executionBundleSetsByTypeMap, invokersByName )

        when:
        listenersPoolsHostess.host( objectA )
        listenersPoolsHostess.host( objectB )
        listenersPoolsHostess.host( objectC )
        listenersPoolsHostess.host( objectD )

        def executionBundleSetForEventA = executionBundleSetsByTypeMap.get( ( Type ) EventA.class )
        def executionBundleSetForEventB = executionBundleSetsByTypeMap.get( EventB.class )

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

        ListenersHostess listenersPoolsHostess = new ListenersHostess( executionBundleSetsByTypeMap, invokersByName )

        when:
        listenersPoolsHostess.host( objectA )
        listenersPoolsHostess.host( objectB )
        listenersPoolsHostess.host( objectA )
        listenersPoolsHostess.host( objectB )

        def executionBundleSetForEventA = executionBundleSetsByTypeMap.get( ( Type ) EventA.class )
        def executionBundleSetForEventB = executionBundleSetsByTypeMap.get( EventB.class )

        then:
        executionBundleSetForEventA != null
        executionBundleSetForEventA.size() == 1

        executionBundleSetForEventB != null
        executionBundleSetForEventB.size() == 1
    }
}
