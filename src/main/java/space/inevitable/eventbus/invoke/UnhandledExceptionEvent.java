package space.inevitable.eventbus.invoke;

public class UnhandledExceptionEvent {
    final Exception exception;

    public UnhandledExceptionEvent(Exception exception) {
        this.exception = exception;
    }
}
