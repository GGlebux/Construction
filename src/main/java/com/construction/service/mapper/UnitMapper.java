package com.construction.service.mapper;

import com.construction.domain.BuildingProject;
import com.construction.domain.Unit;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring", uses = { PhotoMapperHelper.class })
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {
    @Mapping(target = "project", source = "project", qualifiedByName = "buildingProjectId")
    @Mapping(target = "photos", source = "photos", qualifiedByName = "photoLinks")
    UnitDTO toDto(Unit s);

    @Override
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "project", ignore = true)
    Unit toEntity(UnitDTO dto);

    @Override
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "project", ignore = true)
    void partialUpdate(@MappingTarget Unit entity, UnitDTO dto);

    @Named("buildingProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingProjectDTO toDtoBuildingProjectId(BuildingProject buildingProject);
}
