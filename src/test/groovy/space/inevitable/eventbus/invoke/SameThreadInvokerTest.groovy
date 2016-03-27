package space.inevitable.eventbus.invoke

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.ListenersStubsHolder
import space.inevitable.eventbus.post.ExecutionBundleBuilderForTest
import spock.lang.Specification

import static org.mockito.Mockito.mock

class SameThreadInvokerTest extends Specification {
    def "Invoke should call the methodA of the ListenerA instance"() {
        given:
        EventBus eventBus = mock(EventBus)

        def eventA = new ListenersStubsHolder.EventA();
        def executionBundle = ExecutionBundleBuilderForTest.buildExecutionBundleSameThread()

        def sameThreadInvoker = new SameThreadInvoker(eventBus)
        def listenerA = (ListenersStubsHolder.ListenerA) executionBundle.getListener()

        when:
        sameThreadInvoker.invoke(executionBundle, eventA)

        then:
        listenerA.wasMethodAInvoked()
    }
}
