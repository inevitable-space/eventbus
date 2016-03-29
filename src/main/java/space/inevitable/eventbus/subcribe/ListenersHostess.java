package space.inevitable.eventbus.subcribe;


import space.inevitable.eventbus.Subscribe;
import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.beans.ExecutionBundleBuilder;
import space.inevitable.eventbus.beans.MethodData;
import space.inevitable.eventbus.collections.ExecutionBundleSet;
import space.inevitable.eventbus.collections.ExecutionBundleSetsByType;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.reflection.AnnotatedMethodsExtractor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;


public final class ListenersHostess {
    private final ExecutionBundleSetsByType executionBundleSetsByType;
    private final InvokersByName invokersByName;

    public ListenersHostess(final ExecutionBundleSetsByType executionBundleSetsByType, final InvokersByName invokersByName) {
        this.executionBundleSetsByType = executionBundleSetsByType;
        this.invokersByName = invokersByName;
    }

    public void host(final Object listener) {
        final List<Method> annotatedMethods = extractAnnotatedMethods(listener);
        final List<MethodData> methodsData = buildMethodsData(annotatedMethods);

        for (final MethodData methodData : methodsData) {
            final ExecutionBundle executionBundle = buildExecutionBundle(listener, methodData);

            final Type type = methodData.getEventType();
            final ExecutionBundleSet executionBundleSet = getExecutionBundleSetForType(type);
            executionBundleSet.add(executionBundle);
        }
    }

    private ExecutionBundle buildExecutionBundle(final Object listener, final MethodData methodData) {
        final ExecutionBundleBuilder executionBundleBuilder = new ExecutionBundleBuilder();

        final Method method = methodData.getMethod();
        final String invokerName = methodData.getInvokerName();
        final Invoker invoker = invokersByName.get(invokerName);

        executionBundleBuilder.setInvoker(invoker);
        executionBundleBuilder.setListener(listener);
        executionBundleBuilder.setMethod(method);

        return executionBundleBuilder.build();
    }

    private ExecutionBundleSet getExecutionBundleSetForType(final Type type) {
        final boolean containsPoolForType = executionBundleSetsByType.containsKey(type);
        ExecutionBundleSet executionBundleSet;

        if (containsPoolForType) {
            executionBundleSet = executionBundleSetsByType.get(type);
        } else {
            executionBundleSet = new ExecutionBundleSet();
            executionBundleSetsByType.put(type, executionBundleSet);
        }

        return executionBundleSet;
    }

    private List<Method> extractAnnotatedMethods(final Object object) {
        final AnnotatedMethodsExtractor annotatedMethodsExtractor = new AnnotatedMethodsExtractor(object, Subscribe.class);
        return annotatedMethodsExtractor.extract();
    }

    private List<MethodData> buildMethodsData(final List<Method> annotatedMethods) {
        final MethodsDataBuilder methodsDataBuilder = new MethodsDataBuilder(annotatedMethods);
        return methodsDataBuilder.build();
    }
}
