package space.inevitable.eventbus.beans;

/**
 * The priority of a subscriber method is given by the priority of its invoker and the order in which
 * was subscribed in the moment of its listener registration
 */
public enum PostOrder {
    LOWER_EXECUTION_PRIORITY_FIRST, HIGHER_EXECUTION_PRIORITY_FIRST
}
