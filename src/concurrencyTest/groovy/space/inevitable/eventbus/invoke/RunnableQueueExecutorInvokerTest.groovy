package space.inevitable.eventbus.invoke


import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.StandardEventBusBuilder
import space.inevitable.eventbus.invoke.stubs.EventBetweenThreads
import space.inevitable.eventbus.invoke.stubs.ListenerOnOtherThread
import space.inevitable.thread.RunnableQueueExecutor
import space.inevitable.thread.RunnableQueueExecutorLooperThread
import spock.lang.Specification

class RunnableQueueExecutorInvokerTest extends Specification {
    RunnableQueueExecutor runnableQueueExecutor
    EventBus eventBus
    Thread thread

    def setup() {
        StandardEventBusBuilder standardEventBusBuilder = new StandardEventBusBuilder()
        eventBus = standardEventBusBuilder.build()

        runnableQueueExecutor = new RunnableQueueExecutorLooperThread()
        thread = (Thread) runnableQueueExecutor

        Invoker invoker = new RunnableQueueExecutorInvoker(eventBus, runnableQueueExecutor)
        eventBus.addInvoker(invoker)

        runnableQueueExecutor.start()
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
}
