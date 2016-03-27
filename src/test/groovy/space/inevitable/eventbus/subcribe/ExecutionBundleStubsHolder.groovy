package space.inevitable.eventbus.subcribe

import space.inevitable.eventbus.EventBusI
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.Invoker
import space.inevitable.eventbus.invoke.SameThreadInvoker

import java.lang.reflect.Method

import static org.mockito.Mockito.mock
import static space.inevitable.eventbus.ListenersStubsHolder.*

public class ExecutionBundleStubsHolder {
    static ExecutionBundle executionBundleA
    static ExecutionBundle executionBundleACopy
    static ExecutionBundle executionBundleB

    static {
        init()
    }

    private static init() {
        EventBusI eventBus = mock(EventBusI)

        ListenerA listenerA = new ListenerA()
        Method methodA = listenerA.getClass().getMethod("methodA", EventA.class)
        Invoker invoker = new SameThreadInvoker(eventBus)

        executionBundleA = new ExecutionBundle(listenerA, methodA, invoker)
        executionBundleACopy = new ExecutionBundle(listenerA, methodA, invoker)

        ListenerB listenerB = new ListenerB()
        methodA = listenerB.getClass().getMethod("methodA", EventB.class)

        executionBundleB = new ExecutionBundle(listenerB, methodA, invoker)
    }
}

