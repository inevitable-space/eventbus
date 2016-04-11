package space.inevitable.eventbus.register;


import space.inevitable.eventbus.Subscribe;
import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.beans.ExecutionBundleBuilder;
import space.inevitable.eventbus.beans.MethodData;
import space.inevitable.eventbus.collections.ExecutionBundles;
import space.inevitable.eventbus.collections.ExecutionBundlesByType;
import space.inevitable.eventbus.collections.InvokersByName;
import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.reflection.AnnotatedMethodsExtractor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;


public final class ListenersHostess {
    private final ExecutionBundlesByType executionBundlesByType;
    private final InvokersByName invokersByName;

    public ListenersHostess(final ExecutionBundlesByType executionBundlesByType, final InvokersByName invokersByName) {
        this.executionBundlesByType = executionBundlesByType;
        this.invokersByName = invokersByName;
    }

    public void register(final Object listener) {
        //TODO : improve the performance by creating a cache of MethodsData stored by the type of the listener

        final List<Method> annotatedMethods = extractAnnotatedMethods(listener);
        final List<MethodData> methodsData = buildMethodsData(annotatedMethods);

        for (final MethodData methodData : methodsData) {
            final ExecutionBundle executionBundle = buildExecutionBundle(listener, methodData);

            final Type type = methodData.getEventType();
            final ExecutionBundles executionBundles = executionBundlesByType.get(type);
            executionBundles.add(executionBundle);
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
            final boolean containsSetForType = executionBundlesByType.containsKey(type);

            if (containsSetForType) {
                removeExecutionBundleFromSet(executionBundle, type);
            }
        }
    }

    private void removeExecutionBundleFromSet(final ExecutionBundle executionBundle, final Type type) {
        final ExecutionBundles executionBundles = executionBundlesByType.get(type);
        executionBundles.remove(executionBundle);
    }
}
