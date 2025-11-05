package com.construction.domain;

import static com.construction.domain.BookingTestSamples.*;
import static com.construction.domain.ClientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void bookingsTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Booking bookingBack = getBookingRandomSampleGenerator();

        client.addBookings(bookingBack);
        assertThat(client.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getClient()).isEqualTo(client);

        client.removeBookings(bookingBack);
        assertThat(client.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getClient()).isNull();

        client.bookings(new HashSet<>(Set.of(bookingBack)));
        assertThat(client.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getClient()).isEqualTo(client);

        client.setBookings(new HashSet<>());
        assertThat(client.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getClient()).isNull();
    }
}
