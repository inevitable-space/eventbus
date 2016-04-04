package space.inevitable.thread

import spock.lang.Specification

import static space.inevitable.thread.TestRunnableClassesHolder.TestRunnableA
import static space.inevitable.thread.TestRunnableClassesHolder.TestRunnableB

class RunnableQueueExecutorLooperThreadConcurrentTest extends Specification {
    def "breakLoop should avoid testRunnableC to be executed and TestRunnableA executed once"() {
        given:
        RunnableQueueExecutorLooperThread executorThread = new RunnableQueueExecutorLooperThread()
        TestRunnableA testRunnableA = new TestRunnableA()
        TestRunnableB testRunnableB = new TestRunnableB()

        TestRunnableA testRunnableC = new TestRunnableA()

        when:
        executorThread.start()

        executorThread.add(testRunnableA)
        executorThread.add(testRunnableA)

        executorThread.add(testRunnableB)
        executorThread.add(testRunnableB)
        executorThread.add(testRunnableB)
        executorThread.add(testRunnableB)

        Thread.sleep(10)
        executorThread.breakLoop()

        executorThread.add(testRunnableC)
        executorThread.join()

        then:
        testRunnableA.count > 0
        testRunnableC.count == 0
    }
}


