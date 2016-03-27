package space.inevitable.eventbus;

public class ListenersStubsHolder {
    public static class EventA {
    }

    public static final class EventB {
    }

    public static final class EventC {
    }

    public static final class ListenerA {
        private boolean methodAInvoked;

        public boolean wasMethodAInvoked() {
            return methodAInvoked;
        }

        @Subscribe
        public void methodA(final EventA eventA) {
            methodAInvoked = true;
        }
    }

    public static final class ListenerB {
        @Subscribe
        public void methodA(final EventB eventB) {
        }
    }

    public static final class ListenerC {
        @SuppressWarnings("EmptyMethod") //Testing purposes
        @Subscribe("TestInvokerName")
        public void methodA(final EventC eventC) {
        }
    }
}
