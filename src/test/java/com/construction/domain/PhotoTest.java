package com.construction.domain;

import static com.construction.domain.BuildingProjectTestSamples.*;
import static com.construction.domain.PhotoTestSamples.*;
import static com.construction.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Photo.class);
        Photo photo1 = getPhotoSample1();
        Photo photo2 = new Photo();
        assertThat(photo1).isNotEqualTo(photo2);

        photo2.setId(photo1.getId());
        assertThat(photo1).isEqualTo(photo2);

        photo2 = getPhotoSample2();
        assertThat(photo1).isNotEqualTo(photo2);
    }

    @Test
    void projectTest() throws Exception {
        Photo photo = getPhotoRandomSampleGenerator();
        BuildingProject buildingProjectBack = getBuildingProjectRandomSampleGenerator();

        photo.setProject(buildingProjectBack);
        assertThat(photo.getProject()).isEqualTo(buildingProjectBack);

        photo.project(null);
        assertThat(photo.getProject()).isNull();
    }

    @Test
    void unitTest() throws Exception {
        Photo photo = getPhotoRandomSampleGenerator();
        Unit unitBack = getUnitRandomSampleGenerator();

        photo.setUnit(unitBack);
        assertThat(photo.getUnit()).isEqualTo(unitBack);

        photo.unit(null);
        assertThat(photo.getUnit()).isNull();
    }
}
