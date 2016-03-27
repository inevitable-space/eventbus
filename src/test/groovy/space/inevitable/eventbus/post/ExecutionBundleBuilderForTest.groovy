package space.inevitable.eventbus.post

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.ListenersStubsHolder.EventA
import space.inevitable.eventbus.ListenersStubsHolder.ListenerA
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.SameThreadInvoker

import static org.mockito.Mockito.mock

public class ExecutionBundleBuilderForTest {
    public static ExecutionBundle buildExecutionBundleSameThread() {
        EventBus eventBus = mock(EventBus)

        ListenerA listenerA = new ListenerA();
        def method = ListenerA.class.getMethod("methodA", EventA.class)

        def sameThreadInvoker = new SameThreadInvoker(eventBus);
        new ExecutionBundle(listenerA, method, sameThreadInvoker);
    }
}
