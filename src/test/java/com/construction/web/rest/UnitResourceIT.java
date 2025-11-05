package com.construction.web.rest;

import static com.construction.domain.UnitAsserts.*;
import static com.construction.web.rest.TestUtil.createUpdateProxyForBean;
import static com.construction.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.construction.IntegrationTest;
import com.construction.domain.BuildingProject;
import com.construction.domain.Unit;
import com.construction.domain.enumeration.UnitStatus;
import com.construction.domain.enumeration.UnitType;
import com.construction.repository.UnitRepository;
import com.construction.service.dto.UnitDTO;
import com.construction.service.mapper.UnitMapper;
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
 * Integration tests for the {@link UnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnitResourceIT {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AREA = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA = new BigDecimal(2);
    private static final BigDecimal SMALLER_AREA = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;
    private static final Integer SMALLER_FLOOR = 1 - 1;

    private static final UnitType DEFAULT_TYPE = UnitType.STUDIO;
    private static final UnitType UPDATED_TYPE = UnitType.ONE_ROOM;

    private static final UnitStatus DEFAULT_STATUS = UnitStatus.AVAILABLE;
    private static final UnitStatus UPDATED_STATUS = UnitStatus.RESERVED;

    private static final Instant DEFAULT_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitMockMvc;

    private Unit unit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit()
            .location(DEFAULT_LOCATION)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .area(DEFAULT_AREA)
            .floor(DEFAULT_FLOOR)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .completionDate(DEFAULT_COMPLETION_DATE);
        // Add required entity
        BuildingProject buildingProject;
        if (TestUtil.findAll(em, BuildingProject.class).isEmpty()) {
            buildingProject = BuildingProjectResourceIT.createEntity(em);
            em.persist(buildingProject);
            em.flush();
        } else {
            buildingProject = TestUtil.findAll(em, BuildingProject.class).get(0);
        }
        unit.setProject(buildingProject);
        return unit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity(EntityManager em) {
        Unit unit = new Unit()
            .location(UPDATED_LOCATION)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .area(UPDATED_AREA)
            .floor(UPDATED_FLOOR)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE);
        // Add required entity
        BuildingProject buildingProject;
        if (TestUtil.findAll(em, BuildingProject.class).isEmpty()) {
            buildingProject = BuildingProjectResourceIT.createUpdatedEntity(em);
            em.persist(buildingProject);
            em.flush();
        } else {
            buildingProject = TestUtil.findAll(em, BuildingProject.class).get(0);
        }
        unit.setProject(buildingProject);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    void createUnit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);
        var returnedUnitDTO = om.readValue(
            restUnitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UnitDTO.class
        );

        // Validate the Unit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUnit = unitMapper.toEntity(returnedUnitDTO);
        assertUnitUpdatableFieldsEquals(returnedUnit, getPersistedUnit(returnedUnit));
    }

    @Test
    @Transactional
    void createUnitWithExistingId() throws Exception {
        // Create the Unit with an existing ID
        unit.setId(1L);
        UnitDTO unitDTO = unitMapper.toDto(unit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        unit.setPrice(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        unit.setArea(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFloorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        unit.setFloor(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        unit.setType(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        unit.setStatus(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(sameNumber(DEFAULT_AREA))))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));
    }

    @Test
    @Transactional
    void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.area").value(sameNumber(DEFAULT_AREA)))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()));
    }

    @Test
    @Transactional
    void getUnitsByIdFiltering() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        Long id = unit.getId();

        defaultUnitFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUnitFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUnitFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUnitsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where location equals to
        defaultUnitFiltering("location.equals=" + DEFAULT_LOCATION, "location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllUnitsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where location in
        defaultUnitFiltering("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION, "location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllUnitsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where location is not null
        defaultUnitFiltering("location.specified=true", "location.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByLocationContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where location contains
        defaultUnitFiltering("location.contains=" + DEFAULT_LOCATION, "location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllUnitsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where location does not contain
        defaultUnitFiltering("location.doesNotContain=" + UPDATED_LOCATION, "location.doesNotContain=" + DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price equals to
        defaultUnitFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price in
        defaultUnitFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price is not null
        defaultUnitFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price is greater than or equal to
        defaultUnitFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price is less than or equal to
        defaultUnitFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price is less than
        defaultUnitFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where price is greater than
        defaultUnitFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllUnitsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where description equals to
        defaultUnitFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUnitsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where description in
        defaultUnitFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUnitsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where description is not null
        defaultUnitFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where description contains
        defaultUnitFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUnitsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where description does not contain
        defaultUnitFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area equals to
        defaultUnitFiltering("area.equals=" + DEFAULT_AREA, "area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area in
        defaultUnitFiltering("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA, "area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area is not null
        defaultUnitFiltering("area.specified=true", "area.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area is greater than or equal to
        defaultUnitFiltering("area.greaterThanOrEqual=" + DEFAULT_AREA, "area.greaterThanOrEqual=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area is less than or equal to
        defaultUnitFiltering("area.lessThanOrEqual=" + DEFAULT_AREA, "area.lessThanOrEqual=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area is less than
        defaultUnitFiltering("area.lessThan=" + UPDATED_AREA, "area.lessThan=" + DEFAULT_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where area is greater than
        defaultUnitFiltering("area.greaterThan=" + SMALLER_AREA, "area.greaterThan=" + DEFAULT_AREA);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor equals to
        defaultUnitFiltering("floor.equals=" + DEFAULT_FLOOR, "floor.equals=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor in
        defaultUnitFiltering("floor.in=" + DEFAULT_FLOOR + "," + UPDATED_FLOOR, "floor.in=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor is not null
        defaultUnitFiltering("floor.specified=true", "floor.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor is greater than or equal to
        defaultUnitFiltering("floor.greaterThanOrEqual=" + DEFAULT_FLOOR, "floor.greaterThanOrEqual=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor is less than or equal to
        defaultUnitFiltering("floor.lessThanOrEqual=" + DEFAULT_FLOOR, "floor.lessThanOrEqual=" + SMALLER_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsLessThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor is less than
        defaultUnitFiltering("floor.lessThan=" + UPDATED_FLOOR, "floor.lessThan=" + DEFAULT_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByFloorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where floor is greater than
        defaultUnitFiltering("floor.greaterThan=" + SMALLER_FLOOR, "floor.greaterThan=" + DEFAULT_FLOOR);
    }

    @Test
    @Transactional
    void getAllUnitsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where type equals to
        defaultUnitFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllUnitsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where type in
        defaultUnitFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllUnitsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where type is not null
        defaultUnitFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where status equals to
        defaultUnitFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUnitsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where status in
        defaultUnitFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUnitsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where status is not null
        defaultUnitFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where completionDate equals to
        defaultUnitFiltering("completionDate.equals=" + DEFAULT_COMPLETION_DATE, "completionDate.equals=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllUnitsByCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where completionDate in
        defaultUnitFiltering(
            "completionDate.in=" + DEFAULT_COMPLETION_DATE + "," + UPDATED_COMPLETION_DATE,
            "completionDate.in=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllUnitsByCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where completionDate is not null
        defaultUnitFiltering("completionDate.specified=true", "completionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByProjectIsEqualToSomething() throws Exception {
        BuildingProject project;
        if (TestUtil.findAll(em, BuildingProject.class).isEmpty()) {
            unitRepository.saveAndFlush(unit);
            project = BuildingProjectResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, BuildingProject.class).get(0);
        }
        em.persist(project);
        em.flush();
        unit.setProject(project);
        unitRepository.saveAndFlush(unit);
        Long projectId = project.getId();
        // Get all the unitList where project equals to projectId
        defaultUnitShouldBeFound("projectId.equals=" + projectId);

        // Get all the unitList where project equals to (projectId + 1)
        defaultUnitShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    private void defaultUnitFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUnitShouldBeFound(shouldBeFound);
        defaultUnitShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnitShouldBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(sameNumber(DEFAULT_AREA))))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));

        // Check, that the count call also returns 1
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnitShouldNotBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUnit are not directly saved in db
        em.detach(updatedUnit);
        updatedUnit
            .location(UPDATED_LOCATION)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .area(UPDATED_AREA)
            .floor(UPDATED_FLOOR)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE);
        UnitDTO unitDTO = unitMapper.toDto(updatedUnit);

        restUnitMockMvc
            .perform(put(ENTITY_API_URL_ID, unitDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUnitToMatchAllProperties(updatedUnit);
    }

    @Test
    @Transactional
    void putNonExistingUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(put(ENTITY_API_URL_ID, unitDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        partialUpdatedUnit
            .location(UPDATED_LOCATION)
            .area(UPDATED_AREA)
            .floor(UPDATED_FLOOR)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE);

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUnitUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUnit, unit), getPersistedUnit(unit));
    }

    @Test
    @Transactional
    void fullUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        partialUpdatedUnit
            .location(UPDATED_LOCATION)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .area(UPDATED_AREA)
            .floor(UPDATED_FLOOR)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE);

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUnitUpdatableFieldsEquals(partialUpdatedUnit, getPersistedUnit(partialUpdatedUnit));
    }

    @Test
    @Transactional
    void patchNonExistingUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unitDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(unitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unit.setId(longCount.incrementAndGet());

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(unitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the unit
        restUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, unit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return unitRepository.count();
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

    protected Unit getPersistedUnit(Unit unit) {
        return unitRepository.findById(unit.getId()).orElseThrow();
    }

    protected void assertPersistedUnitToMatchAllProperties(Unit expectedUnit) {
        assertUnitAllPropertiesEquals(expectedUnit, getPersistedUnit(expectedUnit));
    }

    protected void assertPersistedUnitToMatchUpdatableProperties(Unit expectedUnit) {
        assertUnitAllUpdatablePropertiesEquals(expectedUnit, getPersistedUnit(expectedUnit));
    }
}
