package space.inevitable.thread

import spock.lang.Specification

import static space.inevitable.thread.TestRunnableClassesHolder.TestRunnableA

class LooperThreadConcurrencyTest extends Specification {
    def "start, breakLoop and join should run the runnable at least one"() {
        given:
        TestRunnableA testRunnable = new TestRunnableA()
        LooperThread looperThread = new LooperThread(testRunnable)

        when:
        looperThread.start()
        looperThread.breakLoop()
        looperThread.join();

        then:
        testRunnable.count > 0
    }
}

