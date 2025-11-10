package com.construction.service;

import com.construction.models.BuildingProject;
import com.construction.repository.BuildingProjectRepository;
import com.construction.dto.BuildingProjectDTO;
import com.construction.mapper.BuildingProjectMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.construction.models.BuildingProject}.
 */
@Service
@Transactional
public class BuildingProjectService {

    private final Logger log = LoggerFactory.getLogger(BuildingProjectService.class);

    private final BuildingProjectRepository buildingProjectRepository;

    private final BuildingProjectMapper buildingProjectMapper;

    public BuildingProjectService(BuildingProjectRepository buildingProjectRepository, BuildingProjectMapper buildingProjectMapper) {
        this.buildingProjectRepository = buildingProjectRepository;
        this.buildingProjectMapper = buildingProjectMapper;
    }

    /**
     * Save a buildingProject.
     *
     * @param buildingProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public BuildingProjectDTO save(BuildingProjectDTO buildingProjectDTO) {
        log.debug("Request to save BuildingProject : {}", buildingProjectDTO);
        BuildingProject buildingProject = buildingProjectMapper.toEntity(buildingProjectDTO);
        buildingProject = buildingProjectRepository.save(buildingProject);
        return buildingProjectMapper.toDto(buildingProject);
    }

    /**
     * Update a buildingProject.
     *
     * @param buildingProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public BuildingProjectDTO update(BuildingProjectDTO buildingProjectDTO) {
        log.debug("Request to update BuildingProject : {}", buildingProjectDTO);
        BuildingProject buildingProject = buildingProjectMapper.toEntity(buildingProjectDTO);
        buildingProject = buildingProjectRepository.save(buildingProject);
        return buildingProjectMapper.toDto(buildingProject);
    }

    /**
     * Partially update a buildingProject.
     *
     * @param buildingProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BuildingProjectDTO> partialUpdate(BuildingProjectDTO buildingProjectDTO) {
        log.debug("Request to partially update BuildingProject : {}", buildingProjectDTO);

        return buildingProjectRepository
            .findById(buildingProjectDTO.getId())
            .map(existingBuildingProject -> {
                buildingProjectMapper.partialUpdate(existingBuildingProject, buildingProjectDTO);

                return existingBuildingProject;
            })
            .map(buildingProjectRepository::save)
            .map(buildingProjectMapper::toDto);
    }

    /**
     * Get one buildingProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuildingProjectDTO> findOne(Long id) {
        log.debug("Request to get BuildingProject : {}", id);
        return buildingProjectRepository.findById(id).map(buildingProjectMapper::toDto);
    }

    /**
     * Delete the buildingProject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BuildingProject : {}", id);
        buildingProjectRepository.deleteById(id);
    }
}
