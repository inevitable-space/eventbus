package space.inevitable.exceptions;

import java.util.List;

public class ExceptionWithSuggestions extends IllegalStateException {
    private final List< String > suggestions;

    public ExceptionWithSuggestions( final String message, final List< String > suggestions ) {
        super( message );
        this.suggestions = suggestions;
    }

    public List< String > getSuggestions() {
        return suggestions;
    }

    @Override
    public String getMessage() {
        final String message = super.getMessage();

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append( message );

        for ( final String suggestion : suggestions ) {
            stringBuilder.append( '\n' );
            stringBuilder.append( suggestion );
        }

        return stringBuilder.toString();
    }
}
