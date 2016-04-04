package space.inevitable.eventbus.collections;


import com.google.common.base.Joiner;
import space.inevitable.eventbus.invoke.Invoker;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class InvokersByName {
    private final Map<String, Invoker> invokersByNameMap;

    public InvokersByName() {
        invokersByNameMap = new ConcurrentHashMap<>();
    }

    public void put(final String invokerName, final Invoker invoker) {
        invokersByNameMap.put(invokerName, invoker);
    }

    public Invoker get(final String invokerName) {
        final Invoker invoker = invokersByNameMap.get(invokerName);
        validateInvokerExistence(invoker, invokerName);
        return invoker;
    }

    private void validateInvokerExistence(final Invoker invoker, final String invokerName) {
        if (invoker != null) {
            return;
        }

        final Set<String> strings = invokersByNameMap.keySet();

        Joiner joiner = Joiner.on("\n    ").skipNulls();
        final String invokers = joiner.join(strings);

        String message = "\nInvoker under the name: [";

        message = message
                .concat(invokerName)
                .concat("] is not registered.")
                .concat("\n--------------------------------------------------------------------------------")
                .concat("\nFIX: use the method addInvoker of the event bus to add an invoker with that name.")
                .concat("\nOR: make sure that the value on the @Subscribe is right.")
                .concat("\nOR: use one of the registered invokers:\n    ")
                .concat(invokers)
        ;

        throw new IllegalStateException(message);
    }
}

