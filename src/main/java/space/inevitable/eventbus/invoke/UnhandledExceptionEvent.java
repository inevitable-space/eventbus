package space.inevitable.eventbus.invoke;

public class UnhandledExceptionEvent {
    private final Throwable reason;

    public UnhandledExceptionEvent(final Throwable reason) {
        this.reason = reason;
    }

    public Throwable getReason() {
        return reason;
    }
}
