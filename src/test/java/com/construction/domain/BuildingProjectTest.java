package com.construction.domain;

import static com.construction.domain.BuildingProjectTestSamples.*;
import static com.construction.domain.PhotoTestSamples.*;
import static com.construction.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BuildingProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingProject.class);
        BuildingProject buildingProject1 = getBuildingProjectSample1();
        BuildingProject buildingProject2 = new BuildingProject();
        assertThat(buildingProject1).isNotEqualTo(buildingProject2);

        buildingProject2.setId(buildingProject1.getId());
        assertThat(buildingProject1).isEqualTo(buildingProject2);

        buildingProject2 = getBuildingProjectSample2();
        assertThat(buildingProject1).isNotEqualTo(buildingProject2);
    }

    @Test
    void unitsTest() throws Exception {
        BuildingProject buildingProject = getBuildingProjectRandomSampleGenerator();
        Unit unitBack = getUnitRandomSampleGenerator();

        buildingProject.addUnits(unitBack);
        assertThat(buildingProject.getUnits()).containsOnly(unitBack);
        assertThat(unitBack.getProject()).isEqualTo(buildingProject);

        buildingProject.removeUnits(unitBack);
        assertThat(buildingProject.getUnits()).doesNotContain(unitBack);
        assertThat(unitBack.getProject()).isNull();

        buildingProject.units(new HashSet<>(Set.of(unitBack)));
        assertThat(buildingProject.getUnits()).containsOnly(unitBack);
        assertThat(unitBack.getProject()).isEqualTo(buildingProject);

        buildingProject.setUnits(new HashSet<>());
        assertThat(buildingProject.getUnits()).doesNotContain(unitBack);
        assertThat(unitBack.getProject()).isNull();
    }

    @Test
    void photosTest() throws Exception {
        BuildingProject buildingProject = getBuildingProjectRandomSampleGenerator();
        Photo photoBack = getPhotoRandomSampleGenerator();

        buildingProject.addPhotos(photoBack);
        assertThat(buildingProject.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getProject()).isEqualTo(buildingProject);

        buildingProject.removePhotos(photoBack);
        assertThat(buildingProject.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getProject()).isNull();

        buildingProject.photos(new HashSet<>(Set.of(photoBack)));
        assertThat(buildingProject.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getProject()).isEqualTo(buildingProject);

        buildingProject.setPhotos(new HashSet<>());
        assertThat(buildingProject.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getProject()).isNull();
    }
}
