package space.inevitable.eventbus.subcribe

import space.inevitable.exceptions.ExceptionWithSuggestions
import spock.lang.Specification
import java.lang.reflect.Method

class ListenerMethodValidatorTest extends Specification {
    def "Validate should throw exception with all the related suggestions actions if method under validation is not valid"() {

        given:
        Method method = MethodSamplesStubHolder.buildInvalidMethod();
        ListenerMethodValidator listenerMethodValidator = new ListenerMethodValidator( method )

        when:
        listenerMethodValidator.validate();

        then:
        ExceptionWithSuggestions exceptionWithSuggestions = thrown ExceptionWithSuggestions
        exceptionWithSuggestions.suggestions.size() == 4;
    }

    def "Validate should not throw exception with if method under validation is valid"() {
        given:
        Method method = MethodSamplesStubHolder.buildValidMethod();
        ListenerMethodValidator listenerMethodValidator = new ListenerMethodValidator( method )

        when:
        listenerMethodValidator.validate();

        then:
        true //exception not thrown
    }
}
