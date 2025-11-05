package com.construction.web.rest;

import static com.construction.domain.BuildingProjectAsserts.*;
import static com.construction.web.rest.TestUtil.createUpdateProxyForBean;
import static com.construction.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.construction.IntegrationTest;
import com.construction.domain.BuildingProject;
import com.construction.domain.enumeration.BuildingType;
import com.construction.repository.BuildingProjectRepository;
import com.construction.service.dto.BuildingProjectDTO;
import com.construction.service.mapper.BuildingProjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuildingProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuildingProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BuildingType DEFAULT_TYPE = BuildingType.MULTI_APARTMENT;
    private static final BuildingType UPDATED_TYPE = BuildingType.PRIVATE_HOUSE_GROUP;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MIN_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_MIN_PRICE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/building-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BuildingProjectRepository buildingProjectRepository;

    @Autowired
    private BuildingProjectMapper buildingProjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildingProjectMockMvc;

    private BuildingProject buildingProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingProject createEntity(EntityManager em) {
        BuildingProject buildingProject = new BuildingProject()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .address(DEFAULT_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .minPrice(DEFAULT_MIN_PRICE)
            .completionDate(DEFAULT_COMPLETION_DATE);
        return buildingProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingProject createUpdatedEntity(EntityManager em) {
        BuildingProject buildingProject = new BuildingProject()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .minPrice(UPDATED_MIN_PRICE)
            .completionDate(UPDATED_COMPLETION_DATE);
        return buildingProject;
    }

    @BeforeEach
    public void initTest() {
        buildingProject = createEntity(em);
    }

    @Test
    @Transactional
    void createBuildingProject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);
        var returnedBuildingProjectDTO = om.readValue(
            restBuildingProjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BuildingProjectDTO.class
        );

        // Validate the BuildingProject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBuildingProject = buildingProjectMapper.toEntity(returnedBuildingProjectDTO);
        assertBuildingProjectUpdatableFieldsEquals(returnedBuildingProject, getPersistedBuildingProject(returnedBuildingProject));
    }

    @Test
    @Transactional
    void createBuildingProjectWithExistingId() throws Exception {
        // Create the BuildingProject with an existing ID
        buildingProject.setId(1L);
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buildingProject.setName(null);

        // Create the BuildingProject, which fails.
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        restBuildingProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buildingProject.setType(null);

        // Create the BuildingProject, which fails.
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        restBuildingProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buildingProject.setAddress(null);

        // Create the BuildingProject, which fails.
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        restBuildingProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuildingProjects() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(sameNumber(DEFAULT_MIN_PRICE))))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));
    }

    @Test
    @Transactional
    void getBuildingProject() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get the buildingProject
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, buildingProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buildingProject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.minPrice").value(sameNumber(DEFAULT_MIN_PRICE)))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()));
    }

    @Test
    @Transactional
    void getBuildingProjectsByIdFiltering() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        Long id = buildingProject.getId();

        defaultBuildingProjectFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBuildingProjectFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBuildingProjectFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where name equals to
        defaultBuildingProjectFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where name in
        defaultBuildingProjectFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where name is not null
        defaultBuildingProjectFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByNameContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where name contains
        defaultBuildingProjectFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where name does not contain
        defaultBuildingProjectFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where type equals to
        defaultBuildingProjectFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where type in
        defaultBuildingProjectFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where type is not null
        defaultBuildingProjectFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where address equals to
        defaultBuildingProjectFiltering("address.equals=" + DEFAULT_ADDRESS, "address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where address in
        defaultBuildingProjectFiltering("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS, "address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where address is not null
        defaultBuildingProjectFiltering("address.specified=true", "address.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByAddressContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where address contains
        defaultBuildingProjectFiltering("address.contains=" + DEFAULT_ADDRESS, "address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where address does not contain
        defaultBuildingProjectFiltering("address.doesNotContain=" + UPDATED_ADDRESS, "address.doesNotContain=" + DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where description equals to
        defaultBuildingProjectFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where description in
        defaultBuildingProjectFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where description is not null
        defaultBuildingProjectFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where description contains
        defaultBuildingProjectFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where description does not contain
        defaultBuildingProjectFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice equals to
        defaultBuildingProjectFiltering("minPrice.equals=" + DEFAULT_MIN_PRICE, "minPrice.equals=" + UPDATED_MIN_PRICE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice in
        defaultBuildingProjectFiltering("minPrice.in=" + DEFAULT_MIN_PRICE + "," + UPDATED_MIN_PRICE, "minPrice.in=" + UPDATED_MIN_PRICE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice is not null
        defaultBuildingProjectFiltering("minPrice.specified=true", "minPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice is greater than or equal to
        defaultBuildingProjectFiltering(
            "minPrice.greaterThanOrEqual=" + DEFAULT_MIN_PRICE,
            "minPrice.greaterThanOrEqual=" + UPDATED_MIN_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice is less than or equal to
        defaultBuildingProjectFiltering("minPrice.lessThanOrEqual=" + DEFAULT_MIN_PRICE, "minPrice.lessThanOrEqual=" + SMALLER_MIN_PRICE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice is less than
        defaultBuildingProjectFiltering("minPrice.lessThan=" + UPDATED_MIN_PRICE, "minPrice.lessThan=" + DEFAULT_MIN_PRICE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByMinPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where minPrice is greater than
        defaultBuildingProjectFiltering("minPrice.greaterThan=" + SMALLER_MIN_PRICE, "minPrice.greaterThan=" + DEFAULT_MIN_PRICE);
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where completionDate equals to
        defaultBuildingProjectFiltering(
            "completionDate.equals=" + DEFAULT_COMPLETION_DATE,
            "completionDate.equals=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where completionDate in
        defaultBuildingProjectFiltering(
            "completionDate.in=" + DEFAULT_COMPLETION_DATE + "," + UPDATED_COMPLETION_DATE,
            "completionDate.in=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuildingProjectsByCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        // Get all the buildingProjectList where completionDate is not null
        defaultBuildingProjectFiltering("completionDate.specified=true", "completionDate.specified=false");
    }

    private void defaultBuildingProjectFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBuildingProjectShouldBeFound(shouldBeFound);
        defaultBuildingProjectShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuildingProjectShouldBeFound(String filter) throws Exception {
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(sameNumber(DEFAULT_MIN_PRICE))))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));

        // Check, that the count call also returns 1
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuildingProjectShouldNotBeFound(String filter) throws Exception {
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuildingProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBuildingProject() throws Exception {
        // Get the buildingProject
        restBuildingProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuildingProject() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buildingProject
        BuildingProject updatedBuildingProject = buildingProjectRepository.findById(buildingProject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBuildingProject are not directly saved in db
        em.detach(updatedBuildingProject);
        updatedBuildingProject
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .minPrice(UPDATED_MIN_PRICE)
            .completionDate(UPDATED_COMPLETION_DATE);
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(updatedBuildingProject);

        restBuildingProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buildingProjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBuildingProjectToMatchAllProperties(updatedBuildingProject);
    }

    @Test
    @Transactional
    void putNonExistingBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buildingProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buildingProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuildingProjectWithPatch() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buildingProject using partial update
        BuildingProject partialUpdatedBuildingProject = new BuildingProject();
        partialUpdatedBuildingProject.setId(buildingProject.getId());

        partialUpdatedBuildingProject.name(UPDATED_NAME).type(UPDATED_TYPE).address(UPDATED_ADDRESS);

        restBuildingProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuildingProject))
            )
            .andExpect(status().isOk());

        // Validate the BuildingProject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuildingProjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBuildingProject, buildingProject),
            getPersistedBuildingProject(buildingProject)
        );
    }

    @Test
    @Transactional
    void fullUpdateBuildingProjectWithPatch() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buildingProject using partial update
        BuildingProject partialUpdatedBuildingProject = new BuildingProject();
        partialUpdatedBuildingProject.setId(buildingProject.getId());

        partialUpdatedBuildingProject
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .minPrice(UPDATED_MIN_PRICE)
            .completionDate(UPDATED_COMPLETION_DATE);

        restBuildingProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuildingProject))
            )
            .andExpect(status().isOk());

        // Validate the BuildingProject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuildingProjectUpdatableFieldsEquals(
            partialUpdatedBuildingProject,
            getPersistedBuildingProject(partialUpdatedBuildingProject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buildingProjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buildingProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buildingProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuildingProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buildingProject.setId(longCount.incrementAndGet());

        // Create the BuildingProject
        BuildingProjectDTO buildingProjectDTO = buildingProjectMapper.toDto(buildingProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingProjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buildingProjectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuildingProject() throws Exception {
        // Initialize the database
        buildingProjectRepository.saveAndFlush(buildingProject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the buildingProject
        restBuildingProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, buildingProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return buildingProjectRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BuildingProject getPersistedBuildingProject(BuildingProject buildingProject) {
        return buildingProjectRepository.findById(buildingProject.getId()).orElseThrow();
    }

    protected void assertPersistedBuildingProjectToMatchAllProperties(BuildingProject expectedBuildingProject) {
        assertBuildingProjectAllPropertiesEquals(expectedBuildingProject, getPersistedBuildingProject(expectedBuildingProject));
    }

    protected void assertPersistedBuildingProjectToMatchUpdatableProperties(BuildingProject expectedBuildingProject) {
        assertBuildingProjectAllUpdatablePropertiesEquals(expectedBuildingProject, getPersistedBuildingProject(expectedBuildingProject));
    }
}
