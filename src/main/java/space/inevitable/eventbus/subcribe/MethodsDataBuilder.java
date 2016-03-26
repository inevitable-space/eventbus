package space.inevitable.eventbus.subcribe;

import space.inevitable.eventbus.beans.MethodData;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public final class MethodsDataBuilder {
    private final List<Method> annotatedMethods;

    public MethodsDataBuilder(final List<Method> annotatedMethods) {
        this.annotatedMethods = annotatedMethods;
    }

    public List<MethodData> build() {
        final List<MethodData> methodsData = new LinkedList<>();

        for (final Method method : annotatedMethods) {
            final MethodData methodData = buildMethodData(method);
            methodsData.add(methodData);
        }

        return methodsData;
    }

    private MethodData buildMethodData(final Method method) {
        final MethodDataBuilder methodDataBuilder = new MethodDataBuilder(method);
        return methodDataBuilder.build();
    }
}
