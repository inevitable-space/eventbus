package space.inevitable.eventbus.register;


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

    public void register(final Object listener) {
        //TODO : improve the performance by creating a cache of MethodsData stored by the type of the listener

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
        final boolean containsSetForType = executionBundleSetsByType.containsKey(type);
        ExecutionBundleSet executionBundleSet;

        if (containsSetForType) {
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

    //TO EVALUATE : From here we could extract other class
    public void unregister(final Object listener) {
        //TODO : once the cache of the MethodsData stored by the type of the listener, improve this logic by making a query on it
        final List<Method> annotatedMethods = extractAnnotatedMethods(listener);
        final List<MethodData> methodsData = buildMethodsData(annotatedMethods);

        for (final MethodData methodData : methodsData) {
            final ExecutionBundle executionBundle = buildExecutionBundle(listener, methodData);

            final Type type = methodData.getEventType();
            final boolean containsSetForType = executionBundleSetsByType.containsKey(type);

            if (containsSetForType) {
                removeExecutionBundleFromSet(executionBundle, type);
            }
        }
    }

    private void removeExecutionBundleFromSet(final ExecutionBundle executionBundle, final Type type) {
        final ExecutionBundleSet executionBundleSet = executionBundleSetsByType.get(type);
        executionBundleSet.remove(executionBundle);
    }
}
