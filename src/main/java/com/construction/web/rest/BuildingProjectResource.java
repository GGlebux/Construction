package com.construction.web.rest;

import com.construction.repository.BuildingProjectRepository;
import com.construction.service.BuildingProjectQueryService;
import com.construction.service.BuildingProjectService;
import com.construction.service.criteria.BuildingProjectCriteria;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.construction.domain.BuildingProject}.
 */
@RestController
@RequestMapping("/api/building-projects")
public class BuildingProjectResource {

    private final Logger log = LoggerFactory.getLogger(BuildingProjectResource.class);

    private static final String ENTITY_NAME = "buildingProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuildingProjectService buildingProjectService;

    private final BuildingProjectRepository buildingProjectRepository;

    private final BuildingProjectQueryService buildingProjectQueryService;

    public BuildingProjectResource(
        BuildingProjectService buildingProjectService,
        BuildingProjectRepository buildingProjectRepository,
        BuildingProjectQueryService buildingProjectQueryService
    ) {
        this.buildingProjectService = buildingProjectService;
        this.buildingProjectRepository = buildingProjectRepository;
        this.buildingProjectQueryService = buildingProjectQueryService;
    }

    /**
     * {@code POST  /building-projects} : Create a new buildingProject.
     *
     * @param buildingProjectDTO the buildingProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buildingProjectDTO, or with status {@code 400 (Bad Request)} if the buildingProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BuildingProjectDTO> createBuildingProject(@Valid @RequestBody BuildingProjectDTO buildingProjectDTO)
        throws URISyntaxException {
        log.debug("REST request to save BuildingProject : {}", buildingProjectDTO);
        if (buildingProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        buildingProjectDTO = buildingProjectService.save(buildingProjectDTO);
        return ResponseEntity.created(new URI("/api/building-projects/" + buildingProjectDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, buildingProjectDTO.getId().toString()))
            .body(buildingProjectDTO);
    }

    /**
     * {@code PUT  /building-projects/:id} : Updates an existing buildingProject.
     *
     * @param id the id of the buildingProjectDTO to save.
     * @param buildingProjectDTO the buildingProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingProjectDTO,
     * or with status {@code 400 (Bad Request)} if the buildingProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buildingProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BuildingProjectDTO> updateBuildingProject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuildingProjectDTO buildingProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BuildingProject : {}, {}", id, buildingProjectDTO);
        if (buildingProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        buildingProjectDTO = buildingProjectService.update(buildingProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildingProjectDTO.getId().toString()))
            .body(buildingProjectDTO);
    }

    /**
     * {@code PATCH  /building-projects/:id} : Partial updates given fields of an existing buildingProject, field will ignore if it is null
     *
     * @param id the id of the buildingProjectDTO to save.
     * @param buildingProjectDTO the buildingProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingProjectDTO,
     * or with status {@code 400 (Bad Request)} if the buildingProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buildingProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buildingProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuildingProjectDTO> partialUpdateBuildingProject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuildingProjectDTO buildingProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuildingProject partially : {}, {}", id, buildingProjectDTO);
        if (buildingProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuildingProjectDTO> result = buildingProjectService.partialUpdate(buildingProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildingProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /building-projects} : get all the buildingProjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildingProjects in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BuildingProjectDTO>> getAllBuildingProjects(
        BuildingProjectCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BuildingProjects by criteria: {}", criteria);

        Page<BuildingProjectDTO> page = buildingProjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /building-projects/count} : count all the buildingProjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBuildingProjects(BuildingProjectCriteria criteria) {
        log.debug("REST request to count BuildingProjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(buildingProjectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /building-projects/:id} : get the "id" buildingProject.
     *
     * @param id the id of the buildingProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buildingProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuildingProjectDTO> getBuildingProject(@PathVariable("id") Long id) {
        log.debug("REST request to get BuildingProject : {}", id);
        Optional<BuildingProjectDTO> buildingProjectDTO = buildingProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildingProjectDTO);
    }

    /**
     * {@code DELETE  /building-projects/:id} : delete the "id" buildingProject.
     *
     * @param id the id of the buildingProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuildingProject(@PathVariable("id") Long id) {
        log.debug("REST request to delete BuildingProject : {}", id);
        buildingProjectService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
