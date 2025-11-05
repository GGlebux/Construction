package com.construction.service.mapper;

import com.construction.domain.BuildingProject;
import com.construction.domain.Unit;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {
    @Mapping(target = "project", source = "project", qualifiedByName = "buildingProjectId")
    UnitDTO toDto(Unit s);

    @Named("buildingProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingProjectDTO toDtoBuildingProjectId(BuildingProject buildingProject);
}
