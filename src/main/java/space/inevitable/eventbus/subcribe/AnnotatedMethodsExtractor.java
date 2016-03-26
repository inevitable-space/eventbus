package space.inevitable.eventbus.subcribe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public final class AnnotatedMethodsExtractor {
    private final Object object;
    private final Class<? extends Annotation> annotationClass;

    public AnnotatedMethodsExtractor(final Object object, final Class<? extends Annotation> annotationClass) {
        this.object = object;
        this.annotationClass = annotationClass;
    }

    public List<Method> extract() {
        final Class<?> clazz = object.getClass();
        final Method[] methods = clazz.getMethods();

        return analyzeMethods(methods);
    }

    private List<Method> analyzeMethods(final Method[] methods) {
        final List<Method> annotatedMethods = new LinkedList<>();

        for (final Method method : methods) {
            final boolean annotationNotPresent = !method.isAnnotationPresent(annotationClass);
            if (annotationNotPresent) {
                continue;
            }
            annotatedMethods.add(method);
        }

        return annotatedMethods;
    }
}
