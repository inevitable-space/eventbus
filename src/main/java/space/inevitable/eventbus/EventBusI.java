package space.inevitable.eventbus;

public interface EventBusI {
    void post(Object event);

    void subscribe(Object listener);
}
