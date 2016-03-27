package space.inevitable.eventbus

import space.inevitable.patterns.Builder
import spock.lang.Specification

class EventBusExample extends Specification {
    def "How to set up and use"() {
        given: //The minimum set up to use the event bus

        Builder<StandardEventBus> standardEventBusBuilder = new StandardEventBusBuilder(); //notice that StandardEventBus needs to be created by the its builder
        EventBus eventBus = standardEventBusBuilder.build(); //notice that EventBus is an Interface that

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

    //TODO : add example of subscriber with no methods annotated in other file
    //TODO : add example of subscriber wich needs to be called in a separated thread
}
