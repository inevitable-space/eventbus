package space.inevitable.eventbus.exception;

/**
 * Is the only reason capable to stop the propagation of the events through the bus by allowing the execution to crash
 */
public class InvokerException extends IllegalStateException {
    private final Exception reason;

    public InvokerException(final Exception reason) {
        this.reason = reason;
    }

    public Exception getReason() {
        return reason;
    }
}
