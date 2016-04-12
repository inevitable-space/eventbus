package space.inevitable.eventbus.collections;

import space.inevitable.eventbus.invoke.Invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InvokersByPriority {
    final private List<Invoker> invokersByPriority;

    public InvokersByPriority() {
        invokersByPriority = new ArrayList<>();
    }

    synchronized public void add(final Invoker invoker) {
        //TODO : STABILITY : THROW : to avoid duplicates
        invokersByPriority.add(invoker);
        Collections.sort(invokersByPriority);
    }

    public List<Invoker> getList() {
        return new LinkedList<>(invokersByPriority);
    }
}
