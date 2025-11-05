package com.construction.service.mapper;

import com.construction.domain.BuildingProject;
import com.construction.domain.Unit;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildingProject} and its DTO {@link BuildingProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { UnitMapper.class, PhotoMapper.class, PhotoMapperHelper.class })
public interface BuildingProjectMapper extends EntityMapper<BuildingProjectDTO, BuildingProject> {
    @Mapping(target = "photos", ignore = true)
    BuildingProject toEntity(BuildingProjectDTO dto);

    @Mapping(target = "photos", source = "photos", qualifiedByName = "photoLinks")
    @Mapping(target = "units", source = "units", qualifiedByName = "unitSimple")
    BuildingProjectDTO toDto(BuildingProject s);

    @Override
    @Mapping(target = "photos", ignore = true)
    void partialUpdate(@MappingTarget BuildingProject entity, BuildingProjectDTO dto);

    @Named("unitSimple")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
