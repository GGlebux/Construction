package com.construction.service;

import com.construction.domain.*;
import com.construction.repository.UnitRepository;
import com.construction.service.criteria.UnitCriteria;
import com.construction.service.dto.UnitDTO;
import com.construction.service.mapper.UnitMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.hibernate.Hibernate.initialize;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

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

    private final EntityManager em;

    public UnitQueryService(UnitRepository unitRepository, UnitMapper unitMapper, EntityManager em) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
        this.em = em;
    }

    /**
     * Return a {@link Page} of {@link UnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UnitDTO> findByCriteria(UnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Unit> specification = createSpecification(criteria);
        Page<Unit> pagedIds = unitRepository.findAll(specification, page);

        List<Unit> unitsWithPhotos = this.findWithPhotosByIdInFresh(
            pagedIds.stream().map(Unit::getId).toList()
        );

        unitsWithPhotos.forEach(unit -> System.err.printf("Photos size {%s}\n",  unit.getPhotos().size()));

        Map<Long, Unit> map = unitsWithPhotos.stream()
            .collect(toMap(Unit::getId, u -> u));

        Page<Unit> merged = pagedIds.map(u -> map.getOrDefault(u.getId(), u));

        merged.forEach(unit -> System.err.printf("Photos size {%s}\n",  unit.getPhotos().size()));

        merged.forEach(unit -> initialize(unit.getPhotos()));

        return merged.map(unitMapper::toDto);
    }

    @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public List<Unit> findWithPhotosByIdInFresh(List<Long> ids) {
        em.clear(); // 💡 сбрасываем кэш первого уровня
        return unitRepository.findWithPhotosByIdIn(ids);
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
            if (criteria.getPhotosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPhotosId(), root -> root.join(Unit_.photos, JoinType.LEFT).get(Photo_.id))
                );
            }
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
}
