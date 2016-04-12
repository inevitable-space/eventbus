package space.inevitable.eventbus

import space.inevitable.eventbus.ListenersStubsHolder.EventA
import space.inevitable.eventbus.ListenersStubsHolder.EventC
import space.inevitable.eventbus.ListenersStubsHolder.ListenerA
import space.inevitable.eventbus.ListenersStubsHolder.ListenerC
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.patterns.Builder
import spock.lang.Specification

class StandardEventBusTest extends Specification {
    ListenerA listenerA
    ListenerC listenerC
    EventBus eventBus
    TestInvoker testInvoker;

    def setup() {
        listenerA = new ListenerA()
        listenerC = new ListenerC()

        Builder<EventBus> standardEventBusBuilder = new StandardEventBusBuilder()
        eventBus = standardEventBusBuilder.build()

        testInvoker = new TestInvoker()
    }

    def "post should invoke methodA of a ListenerA instance when posting EventA instance"() {
        when:
        eventBus.register(listenerA)
        eventBus.post(new EventA())

        then:
        listenerA.wasMethodAInvoked()
    }

    def "post should use the right invoker marked as TestInvokerName when posting EventA instance"() {
        when:
        eventBus.addInvoker(testInvoker)
        eventBus.register(listenerC)
        eventBus.post(new EventC())

        then:
        testInvoker.invoked
    }

    def "register a listener when there is no invoker related to the @Subscribe annotation value should throw an IllegalState exception"() {
        when:
        eventBus.register(listenerC)

        then:
        IllegalStateException illegalStateException = thrown()

        illegalStateException != null
        illegalStateException.message.contains("TestInvokerName")
    }

    def "unregister should avoid post to invoke methodA of a ListenerA instance when posting EventA instance"() {
        when:
        eventBus.register(listenerA)
        eventBus.unregister(listenerA)
        eventBus.post(new EventA())

        then:
        !listenerA.wasMethodAInvoked()
    }

    class TestInvoker extends Invoker {
        private boolean invoked

        @Override
        public void invoke(ExecutionBundle executionBundle, Object eventInstance) {
            invoked = true
        }

        @Override
        public String getName() {
            return "TestInvokerName"
        }

        @Override
        int getExecutionPriority() {
            return 0
        }
    }
}
