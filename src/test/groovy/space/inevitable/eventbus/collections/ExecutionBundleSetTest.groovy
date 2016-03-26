package space.inevitable.eventbus.collections

import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.collections.ExecutionBundleSet
import space.inevitable.eventbus.subcribe.ExecutionBundleStubsHolder
import spock.lang.Specification

class ExecutionBundleSetTest extends Specification {
    def listenerPool = new ExecutionBundleSet()

    private ExecutionBundle executionBundleA = ExecutionBundleStubsHolder.executionBundleA;
    private ExecutionBundle executionBundleACopy = ExecutionBundleStubsHolder.executionBundleACopy;
    private ExecutionBundle executionBundleB = ExecutionBundleStubsHolder.executionBundleB;

    def "Set should accept an executionBundle when is empty"() {
        when:
        listenerPool.add( executionBundleA )

        then:
        listenerPool.size() == 1
    }

    def "Set should accept an executionBundle when is not empty"() {
        when:
        listenerPool.add( executionBundleA )
        listenerPool.add( executionBundleB )

        then:
        listenerPool.size() == 2
    }

    def "Set should not accept an executionBundle if there is an equal execution bundle in it"() {
        when:
        listenerPool.add( executionBundleA )
        listenerPool.add( executionBundleA )
        listenerPool.add( executionBundleACopy )

        then:
        executionBundleACopy.hashCode() == executionBundleA.hashCode()
        listenerPool.size() == 1
    }
}
