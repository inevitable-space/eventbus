package space.inevitable.eventbus;

import space.inevitable.eventbus.invoke.Invoker;
import space.inevitable.eventbus.invoke.SameThreadInvoker;
import space.inevitable.patterns.Builder;

public class StandardEventBusBuilder implements Builder<EventBus> {
    public EventBus build() {
        final EventBus eventBus = new StandardEventBus();

        final Invoker sameThreadInvoker = new SameThreadInvoker(eventBus);
        eventBus.addInvoker(sameThreadInvoker);

        return eventBus;
    }
}
