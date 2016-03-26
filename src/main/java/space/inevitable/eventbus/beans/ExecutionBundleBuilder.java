package space.inevitable.eventbus.beans;

import space.inevitable.eventbus.invoker.Invoker;
import space.inevitable.exceptions.ExceptionWithSuggestionsBuilder;

import java.lang.reflect.Method;

public class ExecutionBundleBuilder {
    private ExceptionWithSuggestionsBuilder exceptionWithSuggestionsBuilder;
    private Invoker invoker;
    private Object listener;
    private Method method;

    public ExecutionBundleBuilder(){
        exceptionWithSuggestionsBuilder = new ExceptionWithSuggestionsBuilder();
    }

    public ExecutionBundle build() {
        validate();

        return new ExecutionBundle(null, null, null);
    }

    private void validate(){
        validateInvoker();
        validateListener();
        validateMethod();
        if( !exceptionWithSuggestionsBuilder.hasSuggestions() ){
            return;
        }

        throw exceptionWithSuggestionsBuilder.build();
    }

    private void validateInvoker() {
        if(invoker != null){
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Invoker] instance. Please use setInvoker to provide one before building.");
    }

    private void validateListener() {
        if(listener != null){
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Object] instance as listener. Please use setListener.");
    }

    private void validateMethod() {
        if(method != null){
            return;
        }

        exceptionWithSuggestionsBuilder.addSuggestion("Can not build with out [Method] instance as listener. Please use setMethod.");
    }

    public void setListener(Object listener) {
        this.listener = listener;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
