package space.inevitable.eventbus.invoker

import space.inevitable.eventbus.ListenersStubsHolder
import space.inevitable.eventbus.post.ExecutionBundleBuilder
import spock.lang.Specification

class SameThreadInvokerTest extends Specification {
    def "Invoke should call the methodA of the ListenerA instance"() {
        given:
        def eventA = new ListenersStubsHolder.EventA();
        def executionBundle = ExecutionBundleBuilder.buildExecutionBundleSameThread()

        def sameThreadInvoker = new SameThreadInvoker()
        def listenerA = ( ListenersStubsHolder.ListenerA ) executionBundle.getListener()

        when:
        sameThreadInvoker.invoke( executionBundle, eventA )

        then:
        listenerA.wasMethodAInvoked()
    }


}
