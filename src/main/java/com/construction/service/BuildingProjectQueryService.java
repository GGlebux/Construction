package com.construction.service;

import com.construction.criteria.BuildingProjectCriteria;
import com.construction.dto.BuildingProjectDTO;
import com.construction.dto.FullProjectDTO;
import com.construction.dto.UnitDTO;
import com.construction.models.*;
import com.construction.repository.BuildingProjectRepository;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import static java.util.stream.Collectors.toSet;

/**
 * Service for executing complex queries for {@link BuildingProject} entities in the database.
 * The main input is a {@link BuildingProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BuildingProjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildingProjectQueryService extends QueryService<BuildingProject> {

    private final Logger log = LoggerFactory.getLogger(BuildingProjectQueryService.class);

    private final BuildingProjectRepository repo;


    public BuildingProjectQueryService(BuildingProjectRepository repo) {
        this.repo = repo;
    }

    /**
     * Return a {@link Page} of {@link BuildingProjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FullProjectDTO> findBySpecification(BuildingProjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        Page<BuildingProject> all = repo.findAll(createSpecification(criteria), page);
        return all.map(this::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(BuildingProjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        return repo.count(createSpecification(criteria));
    }

    protected Specification<BuildingProject> createSpecification(BuildingProjectCriteria criteria) {
        Specification<BuildingProject> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BuildingProject_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BuildingProject_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), BuildingProject_.type));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), BuildingProject_.address));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), BuildingProject_.description));
            }
            if (criteria.getMinPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinPrice(), BuildingProject_.minPrice));
            }
            if (criteria.getCompletionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletionDate(), BuildingProject_.completionDate));
            }
            if (criteria.getUnitsId() != null) {
                specification = specification.and(
                        buildSpecification(criteria.getUnitsId(), root -> root.join(BuildingProject_.units, JoinType.LEFT).get(Unit_.id))
                );
            }
            if (criteria.getPhotosId() != null) {
                specification = specification.and(
                        buildSpecification(criteria.getPhotosId(), root -> root.join(BuildingProject_.photos, JoinType.LEFT).get(Photo_.id))
                );
            }
        }
        return specification;
    }

    private FullProjectDTO toDto(BuildingProject e) {
        return new FullProjectDTO(
                e.getId(),
                e.getName(),
                e.getType(),
                e.getAddress(),
                e.getDescription(),
                e.getMinPrice(),
                e.getCompletionDate(),
                e.getPhotos().stream().map(Photo::getUrl).collect(toSet()),
                e.getUnits().stream().map(u -> new UnitDTO(u.getId())).collect(toSet())
        );
    }
}