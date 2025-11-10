package com.construction.mapper;

import com.construction.dto.BookingDTO;
import com.construction.dto.ClientDTO;
import com.construction.dto.UnitDTO;
import com.construction.models.Booking;
import com.construction.models.Client;
import com.construction.models.Unit;
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

    @Override
    @Mapping(target = "unit", ignore = true)
    Booking toEntity(BookingDTO dto);

    @Override
    @Mapping(target = "unit", ignore = true)
    void partialUpdate(@MappingTarget Booking entity, BookingDTO dto);
}
