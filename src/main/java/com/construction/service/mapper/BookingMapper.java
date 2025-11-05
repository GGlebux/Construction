package com.construction.service.mapper;

import com.construction.domain.Booking;
import com.construction.domain.Client;
import com.construction.domain.Unit;
import com.construction.service.dto.BookingDTO;
import com.construction.service.dto.ClientDTO;
import com.construction.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    BookingDTO toDto(Booking s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
