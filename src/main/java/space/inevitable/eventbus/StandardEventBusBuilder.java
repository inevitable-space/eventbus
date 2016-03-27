package space.inevitable.eventbus;

import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.invoke.SameThreadInvoker;

public class StandardEventBusBuilder {
    public EventBus build() {
        EventBus eventBus = new StandardEventBus();

        final Invoker sameThreadInvoker = new SameThreadInvoker(eventBus);
        eventBus.addInvoker(sameThreadInvoker);

        return eventBus;
    }
}
