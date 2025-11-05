package com.construction.service.mapper;

import com.construction.domain.BuildingProject;
import com.construction.domain.Photo;
import com.construction.domain.Unit;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.service.dto.PhotoDTO;
import com.construction.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {
    @Mapping(target = "project", source = "project", qualifiedByName = "buildingProjectId")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    PhotoDTO toDto(Photo s);

    @Named("buildingProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingProjectDTO toDtoBuildingProjectId(BuildingProject buildingProject);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
