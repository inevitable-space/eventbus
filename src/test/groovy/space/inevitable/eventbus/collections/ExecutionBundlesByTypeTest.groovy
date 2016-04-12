package space.inevitable.eventbus.collections

import space.inevitable.eventbus.beans.ExecutionBundle
import spock.lang.Specification

import java.lang.reflect.Type

class ExecutionBundlesByTypeTest extends Specification {
    ExecutionBundlesByTypeByInvokerName executionBundlesByTypeByInvokerName;
    ExecutionBundle executionBundle;
    Type type;

    def setup() {
        executionBundlesByTypeByInvokerName = new ExecutionBundlesByTypeByInvokerName()
        executionBundle = GroovyMock(ExecutionBundle)
        type = (Type) String

        executionBundlesByTypeByInvokerName.putExecutionBundle(type, "FAKE_INVOKER_NAME", executionBundle)
    }

    def "set should create a relationship between a ExecutionBundleSet and a Type"() {
        expect:
        executionBundlesByTypeByInvokerName.get(type, "FAKE_INVOKER_NAME")
    }

    def "containsKey should return true if it contains a relationship between a key and a executionBundleSet"() {
        expect:
        executionBundlesByTypeByInvokerName.containsKey(type)
    }
}
