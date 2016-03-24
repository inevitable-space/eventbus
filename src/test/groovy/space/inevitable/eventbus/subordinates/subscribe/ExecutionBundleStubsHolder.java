package space.inevitable.eventbus.subordinates.subscribe;

import java.lang.reflect.Method;

import space.inevitable.eventbus.ListenersStubsHolder.EventA;
import space.inevitable.eventbus.ListenersStubsHolder.EventB;
import space.inevitable.eventbus.ListenersStubsHolder.ListenerA;
import space.inevitable.eventbus.ListenersStubsHolder.ListenerB;
import space.inevitable.eventbus.beans.ExecutionBundle;
import space.inevitable.eventbus.invoker.Invoker;
import space.inevitable.eventbus.invoker.SameThreadInvoker;

public final class ExecutionBundleStubsHolder {
    private ExecutionBundleStubsHolder(){
    }

    private static ExecutionBundle executionBundleA;
    private static ExecutionBundle executionBundleACopy;
    private static ExecutionBundle executionBundleB;

    static {
        try {
            init();
        } catch ( NoSuchMethodException ignore ) {
        }
    }

    private static void init() throws NoSuchMethodException {
        ListenerA listenerA = new ListenerA();
        Method methodA = listenerA.getClass().getMethod( "methodA", EventA.class );
        Invoker invoker = new SameThreadInvoker();

        executionBundleA = new ExecutionBundle( listenerA, methodA, invoker );
        executionBundleACopy = new ExecutionBundle( listenerA, methodA, invoker );

        ListenerB listenerB = new ListenerB();
        methodA = listenerB.getClass().getMethod( "methodA", EventB.class );

        executionBundleB = new ExecutionBundle( listenerB, methodA, invoker );
    }

    public static ExecutionBundle getExecutionBundleA() {
        return executionBundleA;
    }

    public static ExecutionBundle getExecutionBundleACopy() {
        return executionBundleACopy;
    }

    public static ExecutionBundle getExecutionBundleB() {
        return executionBundleB;
    }
}
