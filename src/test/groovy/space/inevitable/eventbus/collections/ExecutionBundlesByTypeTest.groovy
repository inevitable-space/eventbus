package space.inevitable.eventbus.collections

import spock.lang.Specification

import java.lang.reflect.Type

class ExecutionBundlesByTypeTest extends Specification {
    ExecutionBundlesByType executionBundleSetsByType;
    ExecutionBundles executionBundleSet;
    Type type;

    def setup() {
        executionBundleSetsByType = new ExecutionBundlesByType()
        executionBundleSet = GroovyMock(ExecutionBundles)
        type = (Type) String
    }

    def "set should create a relationship between a ExecutionBundleSet and a Type"() {
        when:
        executionBundleSetsByType.put(type, executionBundleSet)

        then:
        executionBundleSet == executionBundleSetsByType.get(type)
    }

    def "containsKey should return true if it contains a relationship between a key and a executionBundleSet"() {
        when:
        executionBundleSetsByType.put(type, executionBundleSet)

        then:
        executionBundleSetsByType.containsKey(type)
    }
}
