package com.construction.service;

import com.construction.models.Unit;
import com.construction.repository.UnitRepository;
import com.construction.dto.UnitDTO;
import com.construction.mapper.UnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.construction.models.Unit}.
 */
@Service
@Transactional
public class UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitService.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    /**
     * Update a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitDTO update(UnitDTO unitDTO) {
        log.debug("Request to update Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    /**
     * Partially update a unit.
     *
     * @param unitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UnitDTO> partialUpdate(UnitDTO unitDTO) {
        log.debug("Request to partially update Unit : {}", unitDTO);

        return unitRepository
            .findById(unitDTO.getId())
            .map(existingUnit -> {
                unitMapper.partialUpdate(existingUnit, unitDTO);

                return existingUnit;
            })
            .map(unitRepository::save)
            .map(unitMapper::toDto);
    }

    /**
     * Get one unit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnitDTO> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id).map(unitMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Unit> find(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
