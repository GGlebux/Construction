package com.construction.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BuildingProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BuildingProject getBuildingProjectSample1() {
        return new BuildingProject().id(1L).name("name1").address("address1").description("description1");
    }

    public static BuildingProject getBuildingProjectSample2() {
        return new BuildingProject().id(2L).name("name2").address("address2").description("description2");
    }

    public static BuildingProject getBuildingProjectRandomSampleGenerator() {
        return new BuildingProject()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
