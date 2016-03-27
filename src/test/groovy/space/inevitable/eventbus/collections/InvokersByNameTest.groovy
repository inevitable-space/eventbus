package space.inevitable.eventbus.collections

import space.inevitable.eventbus.invoke.Invoker
import spock.lang.Specification

class InvokersByNameTest extends Specification {
    private InvokersByName invokersByName
    private Invoker invoker;

    def setup(){
        invokersByName = new InvokersByName()
        invoker = GroovyMock(Invoker)
    }

    def "put should create a relationship between the string TEST and an Invoker instance"(){
        when:
        invokersByName.put( "TEST", invoker )
        Invoker outInvoker = invokersByName.get("TEST")

        then:
        outInvoker != null
    }
}
