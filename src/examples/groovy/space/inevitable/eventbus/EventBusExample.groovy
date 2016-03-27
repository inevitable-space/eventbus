package space.inevitable.eventbus

import spock.lang.Specification

class EventBusExample extends Specification {
    def "How to set up and use"() {
        given: //The minimum set up of event bus

        StandardEventBusBuilder standardEventBusBuilder = new StandardEventBusBuilder();
        EventBus eventBus = standardEventBusBuilder.build();

        when: //A instance of SubscriberClass (could be any of your classes) wants to subscribe in to the bus

        SubscriberClass subscriberClass = new SubscriberClass();
        eventBus.subscribe(subscriberClass);

        then: //the subscriberMethod will be invoked when a string is posted in to the bus
        eventBus.post("EVENT INSTANCE");
    }

    /**
     * Used by the examples, could be any class with a method annotated as Subscribe
     */
    private static final class SubscriberClass {
        @Subscribe
        //If there is no value in the annotation the default invoker is set to SameThreadInvoker
        public void subscriberMethod(String event) {
            //Add your code to process the event
        }
    }
}
