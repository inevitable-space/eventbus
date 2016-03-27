package space.inevitable.reflection

import spock.lang.Specification

import java.lang.reflect.Method

class AnnotatedMethodsExtractorTest extends Specification {
    private static class ObjectA {
        @TestAnnotation
        public void methodA() {
        }

        public void methodX() {
        }
    }

    private static class ObjectB {
        @TestAnnotation
        public void methodA() {
        }

        @TestAnnotation
        public void methodB() {
        }

        public void methodX() {
        }
    }

    def "Extract should return a list with 1 method annotated with TestAnnotation"() {
        given:
        ObjectA objectA = new ObjectA();
        AnnotatedMethodsExtractor methodsDataExtractor = new AnnotatedMethodsExtractor(objectA, TestAnnotation.class);

        when:
        List<Method> methods = methodsDataExtractor.extract();

        then:
        methods.size() == 1;

        Method method = methods.get(0);
        method.name == "methodA";
        method.isAnnotationPresent(TestAnnotation.class);
    }

    def "Extract should return a list with 2 methods annotated with TestAnnotation"() {
        given:
        ObjectB objectB = new ObjectB();
        AnnotatedMethodsExtractor methodsDataExtractor = new AnnotatedMethodsExtractor(objectB, TestAnnotation.class);

        when:
        List<Method> methods = methodsDataExtractor.extract();

        then:
        methods.size() == 2;

        Method methodA = methods.get(0);
        methodA.isAnnotationPresent(TestAnnotation.class);

        Method methodB = methods.get(1);
        methodB.isAnnotationPresent(TestAnnotation.class);
    }
}
