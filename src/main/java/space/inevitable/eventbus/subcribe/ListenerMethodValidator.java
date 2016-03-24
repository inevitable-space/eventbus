package space.inevitable.eventbus.subcribe;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import space.inevitable.exceptions.ExceptionWithSuggestions;
import space.inevitable.eventbus.Subscribe;


public final class ListenerMethodValidator {
    private final Method method;
    private final List< String > suggestions;

    public ListenerMethodValidator( final Method method ) {
        this.method = method;
        suggestions = new LinkedList<>();
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
        suggestions.add( suggestion );
    }

    private void throwExceptionIfNeeded() {
        if ( suggestions.isEmpty() ) {
            return;
        }

        final String methodName = method.getName();
        final Class< ? > declaringClass = method.getDeclaringClass();
        final String declaringClassName = declaringClass.getName();

        final String message = String.format( "Method %s of class %s is not eligible to subscribe on the bus.", methodName, declaringClassName );
        throw new ExceptionWithSuggestions( message, suggestions );
    }

    private void verifyIsNotPrivate() {
        final int modifiers = method.getModifiers();
        final boolean isPublic = Modifier.isPublic( modifiers );

        if ( isPublic ) {
            return;
        }

        suggestions.add( "Change the method from private to public." );
    }

    private void verifyMethodIsAnnotated() {
        final boolean isNotAnnotated = !method.isAnnotationPresent( Subscribe.class );
        if ( isNotAnnotated ) {
            suggestions.add( "Annotate the method with @Subscribe." );
        }
    }

    private void verifyParametersNumber() {
        final Class< ? >[] parameterTypes = method.getParameterTypes();

        if ( parameterTypes.length != 1 ) {
            suggestions.add( "Declare one argument only." );
        }
    }
}
