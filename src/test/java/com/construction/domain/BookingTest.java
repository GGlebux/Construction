package com.construction.domain;

import static com.construction.domain.BookingTestSamples.*;
import static com.construction.domain.ClientTestSamples.*;
import static com.construction.domain.UnitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = getBookingSample1();
        Booking booking2 = new Booking();
        assertThat(booking1).isNotEqualTo(booking2);

        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);

        booking2 = getBookingSample2();
        assertThat(booking1).isNotEqualTo(booking2);
    }

    @Test
    void clientTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        booking.setClient(clientBack);
        assertThat(booking.getClient()).isEqualTo(clientBack);

        booking.client(null);
        assertThat(booking.getClient()).isNull();
    }

    @Test
    void unitTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Unit unitBack = getUnitRandomSampleGenerator();

        booking.setUnit(unitBack);
        assertThat(booking.getUnit()).isEqualTo(unitBack);

        booking.unit(null);
        assertThat(booking.getUnit()).isNull();
    }
}
