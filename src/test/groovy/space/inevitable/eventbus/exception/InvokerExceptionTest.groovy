package space.inevitable.eventbus.exception

import space.inevitable.eventbus.StandardEventBusBuilder
import spock.lang.Specification

import static space.inevitable.eventbus.ListenersStubsHolder.*

class InvokerExceptionTest extends Specification {
    def "InvokerException should stop the propagation of the event"() {
        given:
        def builder = new StandardEventBusBuilder()
        def eventBus = builder.build()

        def listenerA1 = new ListenerA()
        def listenerA2 = new ListenerA()
        def listenerA3 = new ListenerA()
        def listenerD = new ListenerD()

        eventBus.register(listenerA1)
        eventBus.register(listenerD)
        eventBus.register(listenerA2)
        eventBus.register(listenerA3)

        def eventA = new EventA()

        when:
        eventBus.post(eventA)

        then:
        thrown(InvokerException)
        listenerA1.wasMethodAInvoked()
        !listenerA2.wasMethodAInvoked()
        !listenerA3.wasMethodAInvoked()
    }
}
