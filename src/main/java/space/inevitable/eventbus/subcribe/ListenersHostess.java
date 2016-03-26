package space.inevitable.eventbus.subcribe;


import space.inevitable.eventbus.Subscribe;
import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.beans.ExecutionBundleBuilder;
import space.inevitable.eventbus.beans.MethodData;
import space.inevitable.eventbus.collections.ExecutionBundleSet;
import space.inevitable.eventbus.invoker.Invoker;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public final class ListenersHostess {
    private final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap;
    private final Map<String, Invoker> invokersByName;


    public ListenersHostess(final Map<Type, ExecutionBundleSet> executionBundleSetsByTypeMap, final Map<String, Invoker> invokersByName) {
        this.executionBundleSetsByTypeMap = executionBundleSetsByTypeMap;
        this.invokersByName = invokersByName;
    }

    public void host(final Object object) {
        final List<Method> annotatedMethods = extractAnnotatedMethods(object);
        final List<MethodData> methodsData = buildMethodsData(annotatedMethods);

        for (final MethodData methodData : methodsData) {
            final ExecutionBundle executionBundle = buildExecutionBundle(object, methodData);

            final Type type = methodData.getEventType();
            final ExecutionBundleSet executionBundleSet = getPoolForType(type);
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

        /*
        if (invoker == null) {
            String message = "Invoker under the name: [" + invokerName + "] is not registered.";
            message += "\n FIX: use the method addInvoker of the event bus.";
            message += "\nSee the JavaDoc of the invoker interface.";
            throw new IllegalStateException(message);
        }

        return new ExecutionBundle(listener, method, invoker);
        */
    }

    private ExecutionBundleSet getPoolForType(final Type type) {
        final boolean containsPoolForType = executionBundleSetsByTypeMap.containsKey(type);

        if (containsPoolForType) {
            return executionBundleSetsByTypeMap.get(type);
        }

        final ExecutionBundleSet executionBundleSet = new ExecutionBundleSet();
        executionBundleSetsByTypeMap.put(type, executionBundleSet);
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
