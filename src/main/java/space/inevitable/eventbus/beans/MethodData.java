package space.inevitable.eventbus.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class MethodData {
    private final String methodName;
    private final Type eventType;
    private final Method method;
    private final String invokerName;

    public MethodData(final String methodName, final Type eventType, final Method method, final String invokerName) {
        this.methodName = methodName;
        this.eventType = eventType;
        this.method = method;
        this.invokerName = invokerName;
    }


    public String getMethodName() {
        return methodName;
    }

    public Type getEventType() {
        return eventType;
    }

    public Method getMethod() {
        return method;
    }

    public String getInvokerName() {
        return invokerName;
    }
}
