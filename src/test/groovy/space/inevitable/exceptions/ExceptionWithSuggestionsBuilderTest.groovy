package space.inevitable.exceptions

import spock.lang.Specification

class ExceptionWithSuggestionsBuilderTest extends Specification {
    private ExceptionWithSuggestionsBuilder exceptionWithSuggestionsBuilder;

    def setup() {
        exceptionWithSuggestionsBuilder = new ExceptionWithSuggestionsBuilder();
    }

    def "build should return an instance of ExceptionWithSuggestions"() {
        when:
        ExceptionWithSuggestions exceptionWithSuggestions = exceptionWithSuggestionsBuilder.build()

        then:
        exceptionWithSuggestions
    }

    def "build should return an instance of ExceptionWithSuggestions with message empty when message and suggestions are not provided"() {
        when:
        ExceptionWithSuggestions exceptionWithSuggestions = exceptionWithSuggestionsBuilder.build()

        then:
        exceptionWithSuggestions.message
    }

    def "build should return an instance of ExceptionWithSuggestions with message equals to TEST when no suggestions are provided and TEST is provided as message"() {
        when:
        exceptionWithSuggestionsBuilder.setMessage("TEST")
        ExceptionWithSuggestions exceptionWithSuggestions = exceptionWithSuggestionsBuilder.build()

        then:
        exceptionWithSuggestions.message == "TEST"
    }

    def "build should return an instance of ExceptionWithSuggestions with message equals to TEST\nA when TEST is provided as message and A as only suggestion "() {
        when:
        exceptionWithSuggestionsBuilder.setMessage("TEST")
        exceptionWithSuggestionsBuilder.addSuggestion("A")
        ExceptionWithSuggestions exceptionWithSuggestions = exceptionWithSuggestionsBuilder.build()

        then:
        exceptionWithSuggestions.message == "TEST\nA"
    }

    def "hasNoSuggestions should return false when no suggestions were added"() {
        expect:
        exceptionWithSuggestionsBuilder.hasNoSuggestions()
    }

    def "hasNoSuggestions should return true when suggestions were added"() {
        when:
        exceptionWithSuggestionsBuilder.addSuggestion("A")

        then:
        !exceptionWithSuggestionsBuilder.hasNoSuggestions()
    }
}
