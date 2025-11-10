package com.construction.repository;

import com.construction.models.BuildingProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuildingProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingProjectRepository extends JpaRepository<BuildingProject, Long>, JpaSpecificationExecutor<BuildingProject> {

    @Override
    @EntityGraph(attributePaths = {"photos", "units"})
    Page<BuildingProject> findAll(Specification<BuildingProject> spec, Pageable pageable);
}
