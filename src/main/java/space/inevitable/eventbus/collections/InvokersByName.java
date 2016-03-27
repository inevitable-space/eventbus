package space.inevitable.eventbus.collections;

import space.inevitable.eventbus.invoke.Invoker;

import java.util.Map;
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

        String message = "Invoker under the name: [" + invokerName + "] is not registered.";
        message += "\n FIX: use the method addInvoker of the event bus.";
        message += "\nSee the JavaDoc of the invoker interface.";
        message += "\n FIX: use the method addInvoker of the event bus.";
        message += "\nSee the JavaDoc of the invoker interface.";

        throw new IllegalStateException(message);
    }
}
