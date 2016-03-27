package space.inevitable.eventbus.beans;

import jdk.nashorn.internal.ir.annotations.Immutable;
import space.inevitable.eventbus.invoker.Invoker;

import java.lang.reflect.Method;
import java.util.Objects;

@Immutable
public final class ExecutionBundle {
    private final Object listener;
    private final Method method;
    private final Invoker invoker;

    public ExecutionBundle(final Object listener, final Method method, final Invoker invoker) {
        this.listener = listener;
        this.method = method;
        this.invoker = invoker;
    }

    public Object getListener() {
        return listener;
    }

    public Method getMethod() {
        return method;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    @Override
    public int hashCode() {
        return Objects.hash(listener, method, invoker);
    }

    @Override
    public boolean equals(final Object object) {
        return
                object == this ||
                        object instanceof ExecutionBundle &&
                                object.hashCode() == hashCode()
                ;
    }
}
