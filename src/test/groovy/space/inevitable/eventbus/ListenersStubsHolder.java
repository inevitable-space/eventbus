package space.inevitable.eventbus;

import space.inevitable.eventbus.exception.InvokerException;

import java.io.IOException;

public final class ListenersStubsHolder {
    private ListenersStubsHolder() {
    }

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
            //Empty for testing purposes
        }
    }

    public static final class ListenerC {
        @Subscribe("TestInvokerName")
        public void methodA(final EventC eventC) {
            //Empty for testing purposes
        }
    }

    public static final class ListenerD {
        @Subscribe
        public void methodA(final EventA eventA) {
            final IOException reason = new IOException("Reason");
            throw new InvokerException(reason);
        }
    }
}
