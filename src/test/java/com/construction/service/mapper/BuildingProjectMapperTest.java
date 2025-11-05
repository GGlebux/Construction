package com.construction.service.mapper;

import static com.construction.domain.BuildingProjectAsserts.*;
import static com.construction.domain.BuildingProjectTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingProjectMapperTest {

    private BuildingProjectMapper buildingProjectMapper;

    @BeforeEach
    void setUp() {
        buildingProjectMapper = new BuildingProjectMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBuildingProjectSample1();
        var actual = buildingProjectMapper.toEntity(buildingProjectMapper.toDto(expected));
        assertBuildingProjectAllPropertiesEquals(expected, actual);
    }
}
