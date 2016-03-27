package space.inevitable.eventbus.subcribe;

import space.inevitable.eventbus.Subscribe;
import space.inevitable.eventbus.beans.MethodData;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class MethodDataBuilder {
    private final Method method;

    public MethodDataBuilder(final Method method) {
        this.method = method;
    }

    public MethodData build() {
        validateMethod();

        final String methodName = method.getName();

        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        final Type eventType = genericParameterTypes[0];

        final Subscribe annotation = method.getAnnotation(Subscribe.class);
        final String invokerName = extractInvokerName(annotation);

        return new MethodData(
                methodName,
                eventType,
                method,
                invokerName
        );
    }

    private String extractInvokerName(final Subscribe annotation){
        return annotation.value();
    }

    private void validateMethod() {
        final ListenerMethodValidator methodValidator = new ListenerMethodValidator(method);
        methodValidator.validate();
    }
}
