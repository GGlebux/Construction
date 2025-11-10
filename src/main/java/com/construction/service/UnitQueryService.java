package com.construction.service;

import com.construction.criteria.UnitCriteria;
import com.construction.dto.BuildingProjectDTO;
import com.construction.dto.FullUnitDTO;
import com.construction.dto.UnitDTO;
import com.construction.mapper.UnitMapper;
import com.construction.models.*;
import com.construction.repository.UnitRepository;
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
 * Service for executing complex queries for {@link Unit} entities in the database.
 * The main input is a {@link UnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnitQueryService extends QueryService<Unit> {

    private final Logger log = LoggerFactory.getLogger(UnitQueryService.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;


    public UnitQueryService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    /**
     * Return a {@link Page} of {@link UnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FullUnitDTO> findByCriteria(UnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Unit> specification = createSpecification(criteria);
        return unitRepository.findAll(specification, page).map(this::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Unit> specification = createSpecification(criteria);
        return unitRepository.count(specification);
    }

    /**
     * Function to convert {@link UnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Unit> createSpecification(UnitCriteria criteria) {
        Specification<Unit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Unit_.id));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Unit_.location));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Unit_.price));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Unit_.description));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArea(), Unit_.area));
            }
            if (criteria.getFloor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloor(), Unit_.floor));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Unit_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Unit_.status));
            }
            if (criteria.getCompletionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletionDate(), Unit_.completionDate));
            }
//            if (criteria.getPhotosId() != null) {
//                specification = specification.and(
//                    buildSpecification(criteria.getPhotosId(), root -> root.join(Unit_.photos, JoinType.LEFT).get(Photo_.id))
//                );
//            }
            if (criteria.getBookingsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getBookingsId(), root -> root.join(Unit_.bookings, JoinType.LEFT).get(Booking_.id))
                );
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProjectId(), root -> root.join(Unit_.project, JoinType.LEFT).get(BuildingProject_.id))
                );
            }
        }
        return specification;
    }

    private FullUnitDTO toDto(Unit e) {
        return new FullUnitDTO(
            e.getId(),
            e.getLocation(),
            e.getPrice(),
            e.getDescription(),
                e.getArea(),
                e.getFloor(),
                e.getType(),
                e.getStatus(),
                e.getCompletionDate(),
                new BuildingProjectDTO(e.getProject().getId()),
                e.getPhotos().stream().map(Photo::getUrl).collect(toSet())
        );
    }
}
