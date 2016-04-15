package space.inevitable.eventbus.collections

import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.Invoker
import spock.lang.Specification

class InvokersByPriorityTest extends Specification {
    Invoker invokerA
    Invoker invokerB
    Invoker invokerC
    Invoker invokerD

    def setup() {
        invokerA = new FakeInvoker() {
            @Override
            int getExecutionPriority() {
                return 0
            }
        }

        invokerB = new FakeInvoker() {
            @Override
            int getExecutionPriority() {
                return 1
            }
        }

        invokerC = new FakeInvoker() {
            @Override
            int getExecutionPriority() {
                return 2
            }
        }

        invokerD = new FakeInvoker() {
            @Override
            int getExecutionPriority() {
                return 3
            }
        }
    }

    def "Add"() {
        given:
        InvokersByPriority invokersByPriority = new InvokersByPriority()

        when:
        invokersByPriority.add(invokerD)
        invokersByPriority.add(invokerA)
        invokersByPriority.add(invokerC)
        invokersByPriority.add(invokerB)

        then:
        def invokers = invokersByPriority.getList()
        invokers[0].getExecutionPriority() == 0
        invokers[1].getExecutionPriority() == 1
        invokers[2].getExecutionPriority() == 2
        invokers[3].getExecutionPriority() == 3
    }

    static abstract class FakeInvoker extends Invoker {
        @Override
        void invoke(ExecutionBundle executionBundle, Object eventInstance) {}

        @Override
        String getName() { return null }
    }
}
