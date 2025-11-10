package com.construction.mapper;

import com.construction.models.BuildingProject;
import com.construction.dto.BuildingProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildingProject} and its DTO {@link BuildingProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { UnitMapper.class, PhotoMapper.class })
public interface BuildingProjectMapper extends EntityMapper<BuildingProjectDTO, BuildingProject> {
}
