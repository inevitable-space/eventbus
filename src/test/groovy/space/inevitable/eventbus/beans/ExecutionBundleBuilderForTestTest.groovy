package space.inevitable.eventbus.beans

import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.exceptions.ExceptionWithSuggestions
import spock.lang.Specification

import java.lang.reflect.Method

class ExecutionBundleBuilderForTestTest extends Specification {
    private ExecutionBundleBuilder executionBundleBuilder;
    private Invoker invoker
    private Object listener
    private Method method

    def setup() {
        executionBundleBuilder = new ExecutionBundleBuilder()
        invoker = GroovyMock(Invoker)
        listener = new Object()
        method = MethodSamplesStubHolder.buildInvalidMethod()
    }

    def "build should return an instance of ExecutionBundle"() {
        when:
        executionBundleBuilder.setMethod(method)
        executionBundleBuilder.setListener(listener)
        executionBundleBuilder.setInvoker(invoker)
        def executionBundle = executionBundleBuilder.build()

        then:
        executionBundle
    }

    def "build should throw an instance of ExceptionWithSuggestion when no invoker is provided, with one suggestion related to the invoker"() {
        when:
        executionBundleBuilder.setMethod(method)
        executionBundleBuilder.setListener(listener)
        executionBundleBuilder.build()

        then:
        ExceptionWithSuggestions exceptionWithSuggestions = thrown(ExceptionWithSuggestions)
        exceptionWithSuggestions.suggestions.size() == 1
        final suggestion = exceptionWithSuggestions.suggestions.get(0)
        suggestion.contains("[Invoker]")
        suggestion.contains("setInvoker")
    }

    def "build should throw an instance of ExceptionWithSuggestion when no listener is provided, with one suggestion related to the listener"() {
        when:
        executionBundleBuilder.setInvoker(invoker)
        executionBundleBuilder.setMethod(method)
        executionBundleBuilder.build()

        then:
        ExceptionWithSuggestions exceptionWithSuggestions = thrown(ExceptionWithSuggestions)
        exceptionWithSuggestions.suggestions.size() == 1
        final suggestion = exceptionWithSuggestions.suggestions.get(0)
        suggestion.contains("[Object]")
        suggestion.contains("listener")
        suggestion.contains("setListener")
    }

    def "build should throw an instance of ExceptionWithSuggestion when no method is provided, with one suggestion related to the listener"() {
        when:
        executionBundleBuilder.setInvoker(invoker)
        executionBundleBuilder.setListener(listener)
        executionBundleBuilder.build()

        then:
        ExceptionWithSuggestions exceptionWithSuggestions = thrown(ExceptionWithSuggestions)
        exceptionWithSuggestions.suggestions.size() == 1
        final suggestion = exceptionWithSuggestions.suggestions.get(0)
        suggestion.contains("[Method]")
        suggestion.contains("setMethod")
    }

    def "build should throw an instance of ExceptionWithSuggestion when no method, listener and invoker is provided, with one suggestion related to the listener"() {
        when:
        executionBundleBuilder.build()

        then:
        ExceptionWithSuggestions exceptionWithSuggestions = thrown(ExceptionWithSuggestions)
        exceptionWithSuggestions.suggestions.size() == 3
    }

    def "build should return an instance of ExecutionBundle with all the fields not assigned to null"() {
        when:
        executionBundleBuilder.setMethod(method)
        executionBundleBuilder.setListener(listener)
        executionBundleBuilder.setInvoker(invoker)

        def executionBundle = executionBundleBuilder.build()

        then:
        executionBundle != null
        executionBundle.listener != null
        executionBundle.method != null
        executionBundle.invoker != null
    }
}
