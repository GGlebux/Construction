package com.construction.domain;

import static com.construction.domain.BookingTestSamples.*;
import static com.construction.domain.BuildingProjectTestSamples.*;
import static com.construction.domain.PhotoTestSamples.*;
import static com.construction.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unit.class);
        Unit unit1 = getUnitSample1();
        Unit unit2 = new Unit();
        assertThat(unit1).isNotEqualTo(unit2);

        unit2.setId(unit1.getId());
        assertThat(unit1).isEqualTo(unit2);

        unit2 = getUnitSample2();
        assertThat(unit1).isNotEqualTo(unit2);
    }

    @Test
    void photosTest() throws Exception {
        Unit unit = getUnitRandomSampleGenerator();
        Photo photoBack = getPhotoRandomSampleGenerator();

        unit.addPhotos(photoBack);
        assertThat(unit.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getUnit()).isEqualTo(unit);

        unit.removePhotos(photoBack);
        assertThat(unit.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getUnit()).isNull();

        unit.photos(new HashSet<>(Set.of(photoBack)));
        assertThat(unit.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getUnit()).isEqualTo(unit);

        unit.setPhotos(new HashSet<>());
        assertThat(unit.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getUnit()).isNull();
    }

    @Test
    void bookingsTest() throws Exception {
        Unit unit = getUnitRandomSampleGenerator();
        Booking bookingBack = getBookingRandomSampleGenerator();

        unit.addBookings(bookingBack);
        assertThat(unit.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getUnit()).isEqualTo(unit);

        unit.removeBookings(bookingBack);
        assertThat(unit.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getUnit()).isNull();

        unit.bookings(new HashSet<>(Set.of(bookingBack)));
        assertThat(unit.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getUnit()).isEqualTo(unit);

        unit.setBookings(new HashSet<>());
        assertThat(unit.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getUnit()).isNull();
    }

    @Test
    void projectTest() throws Exception {
        Unit unit = getUnitRandomSampleGenerator();
        BuildingProject buildingProjectBack = getBuildingProjectRandomSampleGenerator();

        unit.setProject(buildingProjectBack);
        assertThat(unit.getProject()).isEqualTo(buildingProjectBack);

        unit.project(null);
        assertThat(unit.getProject()).isNull();
    }
}
