package space.inevitable.exceptions;

import java.util.LinkedList;
import java.util.List;

public class ExceptionWithSuggestionsBuilder {
    private final List<String> suggestions;
    private String message;

    public ExceptionWithSuggestionsBuilder() {
        suggestions = new LinkedList<>();
    }

    public ExceptionWithSuggestions build() {
        return new ExceptionWithSuggestions(message, suggestions);
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void addSuggestion(final String suggestion) {
        suggestions.add(suggestion);
    }

    public boolean hasSuggestions() {
        return !suggestions.isEmpty();
    }
}
