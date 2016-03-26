package space.inevitable.exceptions

import space.inevitable.exceptions.ExceptionWithSuggestions
import spock.lang.Specification

class ExceptionWithSuggestionsTest extends Specification {
    def "getMessage should include the suggestions"() {
        given:
        List<String> suggestions = ["Suggestion A", "Suggestion B", "Suggestion C"]
        String message = "Message"

        ExceptionWithSuggestions exceptionWithSuggestions = new ExceptionWithSuggestions( message, suggestions )

        when:
        String entireMessage = exceptionWithSuggestions.getMessage()

        then:
        entireMessage == "Message\nSuggestion A\nSuggestion B\nSuggestion C"
    }
}
