package com.construction.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UnitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Unit getUnitSample1() {
        return new Unit().id(1L).location("location1").description("description1").floor(1);
    }

    public static Unit getUnitSample2() {
        return new Unit().id(2L).location("location2").description("description2").floor(2);
    }

    public static Unit getUnitRandomSampleGenerator() {
        return new Unit()
            .id(longCount.incrementAndGet())
            .location(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .floor(intCount.incrementAndGet());
    }
}
