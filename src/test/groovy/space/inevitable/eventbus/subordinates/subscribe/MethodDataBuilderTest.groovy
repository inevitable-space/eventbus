package space.inevitable.eventbus.subordinates.subscribe
import space.inevitable.exceptions.ExceptionWithSuggestions
import space.inevitable.eventbus.beans.MethodData
import space.inevitable.eventbus.subcribe.MethodDataBuilder
import spock.lang.Specification
import java.lang.reflect.Method
import java.lang.reflect.Type
import static space.inevitable.eventbus.subordinates.subscribe.MethodSamplesStubHolder.*

class MethodDataBuilderTest extends Specification {
    def "Build should return a MethodData instance with all the information of the desired method"() {
        given:
        Method method = buildValidMethod()
        MethodDataBuilder methodDataBuilder = new MethodDataBuilder( method );

        when:
        MethodData methodData = methodDataBuilder.build()

        then:
        methodData != null

        methodData.methodName == "validMethod"
        methodData.eventType == ( Type ) String
        methodData.method == method
    }

    @SuppressWarnings( "GroovyUnusedAssignment" )
    def "Build should throw exception if method is not valid"() {
        given:
        Method method = buildInvalidMethod();
        MethodDataBuilder methodDataBuilder = new MethodDataBuilder( method )

        when:
        MethodData methodData = methodDataBuilder.build();

        then:
        thrown ExceptionWithSuggestions
    }
}
