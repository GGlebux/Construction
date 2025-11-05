package com.construction.service.mapper;

import com.construction.domain.BuildingProject;
import com.construction.service.dto.BuildingProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildingProject} and its DTO {@link BuildingProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuildingProjectMapper extends EntityMapper<BuildingProjectDTO, BuildingProject> {}
