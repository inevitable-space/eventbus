package space.inevitable.eventbus.beans

import space.inevitable.eventbus.register.ExecutionBundleStubsHolder
import spock.lang.Specification

class ExecutionBundleTest extends Specification {
    private ExecutionBundle executionBundleA = ExecutionBundleStubsHolder.executionBundleA;
    private ExecutionBundle executionBundleACopy = ExecutionBundleStubsHolder.executionBundleACopy;
    private ExecutionBundle executionBundleB = ExecutionBundleStubsHolder.executionBundleB;

    def "hashCode should be the same for executionBundleA and executionBundleACopy"() {
        expect:
        executionBundleA.hashCode() == executionBundleACopy.hashCode()
    }

    def "hashCode should be different for executionBundleA and executionBundleB"() {
        expect:
        executionBundleA.hashCode() != executionBundleB.hashCode()
    }
}
