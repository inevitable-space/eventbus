package space.inevitable.eventbus.invoke;

public class UnhandledExceptionEvent {
    private final Exception exception;

    public UnhandledExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
