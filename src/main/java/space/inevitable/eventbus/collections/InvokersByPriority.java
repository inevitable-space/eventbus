package space.inevitable.eventbus.collections;

import space.inevitable.eventbus.beans.PostOrder;
import space.inevitable.eventbus.invoke.Invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class InvokersByPriority {
    final private List<Invoker> invokersByPriority;

    public InvokersByPriority() {
        invokersByPriority = new ArrayList<>();
    }

    synchronized public void add(final Invoker invoker) {
        //TODO : STABILITY : THROW : to avoid duplicates
        invokersByPriority.add(invoker);
        Collections.sort(invokersByPriority);
    }

    public List<Invoker> getList(final PostOrder postOrder) {
        final List<Invoker> copy = new LinkedList<>(invokersByPriority);

        if (postOrder == PostOrder.HIGHER_EXECUTION_PRIORITY_FIRST) {
            Collections.reverse(copy);
        }

        return copy;
    }
}
