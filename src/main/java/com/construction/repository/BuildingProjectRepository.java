package com.construction.repository;

import com.construction.domain.BuildingProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuildingProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingProjectRepository extends JpaRepository<BuildingProject, Long>, JpaSpecificationExecutor<BuildingProject> {}
