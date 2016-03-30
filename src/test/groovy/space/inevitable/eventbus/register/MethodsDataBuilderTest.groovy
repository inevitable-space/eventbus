package space.inevitable.eventbus.register

import space.inevitable.eventbus.Subscribe
import space.inevitable.eventbus.beans.MethodData
import spock.lang.Specification

import java.lang.reflect.Method

class MethodsDataBuilderTest extends Specification {

    def "Build should return a list of two methodData objects "() {
        given:
        List<Method> methods = buildMethods()

        when:
        MethodsDataBuilder methodsDataBuilder = new MethodsDataBuilder(methods)
        List<MethodData> methodsData = methodsDataBuilder.build()

        then:
        methodsData.size() == 2
    }

    public static final class MethodsHolder {
        @Subscribe
        public void methodA(String event) {
        }

        @Subscribe
        public void methodB(String event) {
        }
    }

    private static List<Method> buildMethods() {
        List<Method> methods = new LinkedList<>()

        Method methodA = MethodsHolder.class.getMethod("methodA", String);
        Method methodB = MethodsHolder.class.getMethod("methodB", String);

        methods.add(methodA)
        methods.add(methodB)

        methods
    }
}
