package space.inevitable.eventbus.invoke

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.StandardEventBus
import space.inevitable.eventbus.invoke.stubs.*
import space.inevitable.thread.RunnableQueueExecutor
import space.inevitable.thread.RunnableQueueExecutorLooperThread
import spock.lang.Specification

import static space.inevitable.eventbus.beans.PostOrder.HIGHER_EXECUTION_PRIORITY_FIRST

class OrderOfExecutionTest extends Specification {
    RunnableQueueExecutor runnableQueueExecutor
    EventBus eventBus
    Thread thread

    ListenerOnOtherThread listenerOnOtherThreadA
    ListenerOnOtherThread listenerOnOtherThreadB
    ListenerOnSameThread listenerOnSameThreadA
    ListenerOnSameThread listenerOnSameThreadB

    def setup() {
        eventBus = new StandardEventBus()

        runnableQueueExecutor = new RunnableQueueExecutorLooperThread()
        thread = (Thread) runnableQueueExecutor

        def invokerA = new RunnableQueueExecutorInvokerForTest(eventBus, runnableQueueExecutor)
        def invokerB = new SameThreadInvokerForTest(eventBus);

        eventBus.addInvoker(invokerA)
        eventBus.addInvoker(invokerB)

        listenerOnOtherThreadA = new ListenerOnOtherThread(thread)
        listenerOnOtherThreadB = new ListenerOnOtherThread(thread)
        listenerOnSameThreadA = new ListenerOnSameThread()
        listenerOnSameThreadB = new ListenerOnSameThread()

        eventBus.register(listenerOnSameThreadA)
        eventBus.register(listenerOnSameThreadB)

        eventBus.register(listenerOnOtherThreadA)
        eventBus.register(listenerOnOtherThreadB)

        runnableQueueExecutor.start()
        Counter.reset()
    }

    def "Methods on marked with RunnableQueueExecutorInvoker should invoked first by default"() {
        when:
        eventBus.post(new EventBetweenThreads())
        Thread.sleep(5)

        then:
        listenerOnOtherThreadA.orderOfInvocation == 1
        listenerOnOtherThreadB.orderOfInvocation == 2

        listenerOnSameThreadA.orderOfInvocation == 3
        listenerOnSameThreadB.orderOfInvocation == 4
    }

    def "Methods on marked with RunnableQueueExecutorInvoker should invoked last"() {
        when:
        eventBus.post(new EventBetweenThreads(), HIGHER_EXECUTION_PRIORITY_FIRST)
        Thread.sleep(5)

        then:
        listenerOnOtherThreadA.orderOfInvocation == 4
        listenerOnOtherThreadB.orderOfInvocation == 3

        listenerOnSameThreadA.orderOfInvocation == 2
        listenerOnSameThreadB.orderOfInvocation == 1
    }
}
