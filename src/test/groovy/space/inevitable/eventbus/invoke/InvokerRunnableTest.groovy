package space.inevitable.eventbus.invoke

import org.mockito.ArgumentCaptor
import space.inevitable.eventbus.EventBusI
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.post.ExecutionBundleBuilderForTest
import spock.lang.Specification

import java.lang.reflect.Method

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static space.inevitable.eventbus.ListenersStubsHolder.EventA
import static space.inevitable.eventbus.ListenersStubsHolder.ListenerA

class InvokerRunnableTest extends Specification {
    EventA eventA
    ListenerA listener
    Method method
    EventBusI eventBus

    def setup() {
        eventA = new EventA();
        ExecutionBundle executionBundle = ExecutionBundleBuilderForTest.buildExecutionBundleSameThread()

        listener = (ListenerA) executionBundle.listener
        method = executionBundle.method

        eventBus = mock(EventBusI)
    }

    def "Run should execute the method"() {
        given:
        InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, eventA, eventBus);

        when:
        invokerRunnable.run()

        then:
        listener.wasMethodAInvoked()
    }

    def "Run should dispatch a CrashEvent if the method throws an exception"() {
        given:
        InvokerRunnable invokerRunnable = new InvokerRunnable(method, listener, "BAD ARGUMENT", eventBus);

        when:
        invokerRunnable.run()

        then:
        ArgumentCaptor<UnhandledExceptionEvent> argument = ArgumentCaptor.forClass(UnhandledExceptionEvent.class)
        verify(eventBus).post(argument.capture())
    }
}
