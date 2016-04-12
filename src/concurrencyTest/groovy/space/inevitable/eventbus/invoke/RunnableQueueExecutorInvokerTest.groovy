package space.inevitable.eventbus.invoke

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.StandardEventBus
import space.inevitable.eventbus.Subscribe
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.thread.RunnableQueueExecutor
import space.inevitable.thread.RunnableQueueExecutorLooperThread
import spock.lang.Specification

class RunnableQueueExecutorInvokerTest extends Specification {
    RunnableQueueExecutor runnableQueueExecutor
    EventBus eventBus
    Thread thread

    def setup() {
        eventBus = new StandardEventBus()

        runnableQueueExecutor = new RunnableQueueExecutorLooperThread()
        thread = (Thread) runnableQueueExecutor

        def invokerA = new RunnableQueueExecutorInvokerA(eventBus, runnableQueueExecutor)
        def invokerB = new SameThreadInvokerA(eventBus);

        eventBus.addInvoker(invokerA)
        eventBus.addInvoker(invokerB)

        runnableQueueExecutor.start()
        Counter.reset()
    }

    def "RunnableQueueExecutorInvoker should invoke the method listener in the right thread"() {
        given:
        ListenerOnOtherThread listenerOnOtherThread = new ListenerOnOtherThread(thread)
        eventBus.register(listenerOnOtherThread)

        when:
        eventBus.post(new EventBetweenThreads())
        Thread.sleep(5)

        runnableQueueExecutor.breakLoop()

        then:
        listenerOnOtherThread.methodCalled
    }

    def "Events on marked with RunnableQueueExecutorInvoker should invoked first"() {
        given:
        ListenerOnOtherThread listenerOnOtherThreadA = new ListenerOnOtherThread(thread)
        ListenerOnOtherThread listenerOnOtherThreadB = new ListenerOnOtherThread(thread)
        ListenerOnSameThread listenerOnSameThreadA = new ListenerOnSameThread()
        ListenerOnSameThread listenerOnSameThreadB = new ListenerOnSameThread()

        eventBus.register(listenerOnSameThreadA)
        eventBus.register(listenerOnSameThreadB)

        eventBus.register(listenerOnOtherThreadA)
        eventBus.register(listenerOnOtherThreadB)

        when:
        eventBus.post(new EventBetweenThreads())
        Thread.sleep(5)

        then:
        listenerOnOtherThreadA.methodCalled
        listenerOnOtherThreadB.methodCalled

        listenerOnSameThreadA.methodCalled
        listenerOnSameThreadB.methodCalled

        listenerOnOtherThreadA.orderOfInvocation == 1
        listenerOnOtherThreadB.orderOfInvocation == 2

        listenerOnSameThreadA.orderOfInvocation == 3
        listenerOnSameThreadB.orderOfInvocation == 4
    }

    static class EventBetweenThreads {
        Thread threadOfCreation = Thread.currentThread();
    }

    static class ListenerOnOtherThread {
        Thread thread
        boolean methodCalled
        public int orderOfInvocation = -1

        public ListenerOnOtherThread(Thread thread) {
            this.thread = thread
        }

        @Subscribe("RunnableQueueExecutorInvoker")
        public void method(EventBetweenThreads eventBetweenThreads) {
            Thread currentThread = Thread.currentThread()
            Thread threadOfCreation = eventBetweenThreads.threadOfCreation

            if (!currentThread.equals(this.thread) || threadOfCreation.equals(currentThread)) {
                throw new IllegalStateException("NOT IN THE SAME THREAD")
            }

            if (methodCalled) {
                throw new Exception("Invoked twice")
            }
            methodCalled = true
        }
    }

    static class ListenerOnSameThread {
        public int orderOfInvocation = -1
        boolean methodCalled = false

        @Subscribe
        public void method(EventBetweenThreads eventBetweenThreads) {
            if (methodCalled) {
                throw new Exception("Invoked twice")
            }
            methodCalled = true
        }
    }

    static class SameThreadInvokerA extends SameThreadInvoker {
        SameThreadInvokerA(EventBus eventBus) {
            super(eventBus)
        }

        @Override
        public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
            def listener = executionBundle.listener
            def increment = Counter.getAndIncrement()

            listener.orderOfInvocation = increment
            super.invoke(executionBundle, eventInstance)
        }
    }

    static class RunnableQueueExecutorInvokerA extends RunnableQueueExecutorInvoker {
        RunnableQueueExecutorInvokerA(EventBus eventBus, RunnableQueueExecutor runnableQueueExecutor) {
            super(eventBus, runnableQueueExecutor)
        }

        @Override
        public void invoke(final ExecutionBundle executionBundle, final Object eventInstance) {
            def listener = executionBundle.listener
            def increment = Counter.getAndIncrement()

            listener.orderOfInvocation = increment
            super.invoke(executionBundle, eventInstance)
        }
    }

    static class Counter {
        static int counter = 0

        static int reset() {
            counter = 0
        }

        static int getAndIncrement() {
            counter++
            return counter
        }
    }
}
