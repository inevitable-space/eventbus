package space.inevitable.eventbus.subcribe;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import space.inevitable.exceptions.ExceptionWithSuggestions;
import space.inevitable.eventbus.Subscribe;
import space.inevitable.exceptions.ExceptionWithSuggestionsBuilder;


public final class ListenerMethodValidator {
    private final Method method;
    private final ExceptionWithSuggestionsBuilder exceptionWithSuggestionsBuilder;

    public ListenerMethodValidator( final Method method ) {
        this.method = method;
        this.exceptionWithSuggestionsBuilder = new ExceptionWithSuggestionsBuilder();
    }

    public void validate() {
        verifyParametersNumber();
        verifyMethodIsAnnotated();
        verifyIsNotPrivate();
        verifyMethodHasVoidReturnType();
        throwExceptionIfNeeded();
    }

    private void verifyMethodHasVoidReturnType() {
        final Class< ? > returnType = method.getReturnType();
        final boolean isReturnTypeVoid = returnType.equals( Void.TYPE );

        if ( isReturnTypeVoid ) {
            return;
        }

        final String suggestion = String.format( "Make the return type void. Current return type is %s.", returnType );
        exceptionWithSuggestionsBuilder.addSuggestion( suggestion );
    }

    private void throwExceptionIfNeeded() {
        if ( !exceptionWithSuggestionsBuilder.hasSuggestions() ) {
            return;
        }

        final String methodName = method.getName();
        final Class< ? > declaringClass = method.getDeclaringClass();
        final String declaringClassName = declaringClass.getName();

        final String message = String.format( "Method %s of class %s is not eligible to subscribe on the bus.", methodName, declaringClassName );

        exceptionWithSuggestionsBuilder.setMessage(message);
        throw exceptionWithSuggestionsBuilder.build();
    }

    private void verifyIsNotPrivate() {
        final int modifiers = method.getModifiers();
        final boolean isPublic = Modifier.isPublic( modifiers );

        if ( isPublic ) {
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion( "Change the method from private to public." );
    }

    private void verifyMethodIsAnnotated() {
        final boolean isNotAnnotated = !method.isAnnotationPresent( Subscribe.class );
        if ( isNotAnnotated ) {
            exceptionWithSuggestionsBuilder.addSuggestion( "Annotate the method with @Subscribe." );
        }
    }

    private void verifyParametersNumber() {
        final Class< ? >[] parameterTypes = method.getParameterTypes();

        if ( parameterTypes.length != 1 ) {
            exceptionWithSuggestionsBuilder.addSuggestion( "Declare one argument only." );
        }
    }
}
