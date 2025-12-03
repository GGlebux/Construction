package com.construction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class FullClientDTO {
    private Long id;
    private AdminUserDTO adminUser;
    private Set<BookingDTO> bookings;
}
