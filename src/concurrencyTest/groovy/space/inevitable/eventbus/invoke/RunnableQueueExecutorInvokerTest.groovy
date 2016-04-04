package space.inevitable.eventbus.invoke

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.StandardEventBusBuilder
import space.inevitable.eventbus.Subscribe
import space.inevitable.thread.RunnableQueueExecutor
import space.inevitable.thread.RunnableQueueExecutorLooperThread
import spock.lang.Specification

class RunnableQueueExecutorInvokerTest extends Specification {
    RunnableQueueExecutor runnableQueueExecutor
    Invoker invoker
    EventBus eventBus

    def setup() {
        StandardEventBusBuilder standardEventBusBuilder = new StandardEventBusBuilder()

        eventBus = standardEventBusBuilder.build()
        runnableQueueExecutor = new RunnableQueueExecutorLooperThread()
        invoker = new RunnableQueueExecutorInvoker(eventBus, runnableQueueExecutor)

        eventBus.addInvoker(invoker)
    }

    def "RunnableQueueExecutorInvoker should invoke the method listener in the right thread"() {
        given:

        runnableQueueExecutor.start()

        def thread = (Thread) runnableQueueExecutor
        ListenerOnOtherThread listenerOnOtherThread = new ListenerOnOtherThread(thread)
        eventBus.register(listenerOnOtherThread)

        when:
        eventBus.post(new EventBetweenThreads())
        eventBus.post(new EventBetweenThreads())
        eventBus.post(new EventBetweenThreads())

        Thread.sleep(5)

        runnableQueueExecutor.breakLoop()

        then:
        listenerOnOtherThread.methodCalled
    }

    static class EventBetweenThreads {
        Thread threadOfCreation = Thread.currentThread();
    }

    static class ListenerOnOtherThread {
        Thread thread
        boolean methodCalled

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
            methodCalled = true
        }
    }
}
