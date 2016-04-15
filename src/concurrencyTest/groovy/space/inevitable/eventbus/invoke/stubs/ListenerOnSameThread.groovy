package space.inevitable.eventbus.invoke.stubs

import space.inevitable.eventbus.Subscribe

class ListenerOnSameThread {
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
