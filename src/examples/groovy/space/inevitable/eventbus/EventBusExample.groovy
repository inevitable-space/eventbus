package space.inevitable.eventbus

import space.inevitable.patterns.Builder
import spock.lang.Specification

class EventBusExample extends Specification {
    def "How to set up and use"() {
        given: //The minimum put up to use the event bus

        Builder<EventBus> standardEventBusBuilder = new StandardEventBusBuilder(); //notice that StandardEventBus needs to be created by the its builder
        EventBus eventBus = standardEventBusBuilder.build(); //notice that EventBus is an Interface that

        when: //A instance of SubscriberClass (could be any of your classes) wants to register in to the bus

        SubscriberClass subscriberClass = new SubscriberClass();
        eventBus.register(subscriberClass);

        then: //the subscriberMethod will be invoked when a string is posted in to the bus
        eventBus.post("EVENT INSTANCE");
    }

    /**
     * Used by the examples, could be any class with a method annotated as Subscribe
     */
    private static final class SubscriberClass {
        @Subscribe
        //If there is no value in the annotation the default invoker is put to SameThreadInvoker
        public void subscriberMethod(String event) {
            //Add your code to process the event
        }
    }

    //TODO : add example of subscriber with no methods annotated in other file
    //TODO : add example of subscriber which needs to be called in a separated thread
}
