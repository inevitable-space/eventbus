package space.inevitable.eventbus.invoke.stubs

class Counter {
    static int counter = 0

    static int reset() {
        counter = 0
    }

    static int getAndIncrement() {
        counter++
        return counter
    }
}
