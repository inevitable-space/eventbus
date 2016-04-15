package space.inevitable.eventbus.invoke.stubs

import space.inevitable.eventbus.EventBus
import space.inevitable.eventbus.beans.ExecutionBundle
import space.inevitable.eventbus.invoke.SameThreadInvoker

class SameThreadInvokerForTest extends SameThreadInvoker {
    SameThreadInvokerForTest(EventBus eventBus) {
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
