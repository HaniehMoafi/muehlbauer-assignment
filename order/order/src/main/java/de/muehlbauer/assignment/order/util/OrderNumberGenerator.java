package de.muehlbauer.assignment.order.util;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {

    private final static AtomicInteger counter = new AtomicInteger(0);

   public static int getValue() {
        increment();
        return counter.get();
    }

    static void increment() {
        counter.incrementAndGet();
    }

/*    @Scheduled(cron = "0 0 0 * * *")
    void reset() {
        counter.set(0);
    }*/
}
