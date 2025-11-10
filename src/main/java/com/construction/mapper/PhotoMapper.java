package com.construction.mapper;

import com.construction.models.BuildingProject;
import com.construction.models.Photo;
import com.construction.models.Unit;
import com.construction.dto.BuildingProjectDTO;
import com.construction.dto.PhotoDTO;
import com.construction.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {
    @Mapping(target = "project", source = "project", qualifiedByName = "buildingProjectId")
//    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    PhotoDTO toDto(Photo s);

    @Override
    @Mapping(target = "project.photos", ignore = true)
    @Mapping(target = "project.units", ignore = true)
    @Mapping(target = "unit.photos", ignore = true)
    @Mapping(target = "unit.project", ignore = true)
    Photo toEntity(PhotoDTO dto);

    @Override
    @Mapping(target = "project.photos", ignore = true)
    @Mapping(target = "project.units", ignore = true)
    @Mapping(target = "unit.photos", ignore = true)
    @Mapping(target = "unit.project", ignore = true)
    void partialUpdate(@MappingTarget Photo entity, PhotoDTO dto);

    @Named("buildingProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingProjectDTO toDtoBuildingProjectId(BuildingProject buildingProject);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
