package space.inevitable.eventbus;

import org.junit.Test;

public class EventBusExample {
    @Test
    public void subscribe() {
        //Set up the standard event bus
        StandardEventBusBuilder standardEventBusBuilder = new StandardEventBusBuilder();
        EventBus eventBus = standardEventBusBuilder.build();

        //Client to be subscribed in to the bus
        SubscriberClass subscriberClass = new SubscriberClass();

        //After the next line the subscriberMethod will be invoked when a string is posted in to the bus
        eventBus.subscribe(subscriberClass);

        eventBus.post("EVENT INSTANCE");
    }

    private static final class SubscriberClass {
        @Subscribe //If there is no value in the annotation the default invoker is set to SameThreadInvoker
        public void subscriberMethod(String event) {
            //Add your code to process the event
        }
    }

}
