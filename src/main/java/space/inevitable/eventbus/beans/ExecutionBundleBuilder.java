package space.inevitable.eventbus.beans;

import jdk.nashorn.internal.ir.annotations.Immutable;
import space.inevitable.eventbus.invoker.Invoker;
import space.inevitable.exceptions.ExceptionWithSuggestionsBuilder;

import java.lang.reflect.Method;

@Immutable
public final class ExecutionBundleBuilder {
    private final ExceptionWithSuggestionsBuilder exceptionWithSuggestionsBuilder;
    private Invoker invoker;
    private Object listener;
    private Method method;

    public ExecutionBundleBuilder() {
        exceptionWithSuggestionsBuilder = new ExceptionWithSuggestionsBuilder();
    }

    public ExecutionBundle build() {
        validate();
        return new ExecutionBundle(listener, method, invoker);
    }

    private void validate() {
        validateInvoker();
        validateListener();
        validateMethod();
        if (!exceptionWithSuggestionsBuilder.hasSuggestions()) {
            return;
        }

        exceptionWithSuggestionsBuilder.setMessage("Make sure all the fields are set before trying to build an instance of [ExecutionBundle].");
        throw exceptionWithSuggestionsBuilder.build();
    }

    private void validateInvoker() {
        if (invoker != null) {
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Invoker] instance. Please use setInvoker to provide one before building.");
    }

    private void validateListener() {
        if (listener != null) {
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Object] instance as listener. Please use setListener.");
    }

    private void validateMethod() {
        if (method != null) {
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Method] instance as listener. Please use setMethod.");
    }

    public void setListener(final Object listener) {
        this.listener = listener;
    }

    public void setInvoker(final Invoker invoker) {
        this.invoker = invoker;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }
}
