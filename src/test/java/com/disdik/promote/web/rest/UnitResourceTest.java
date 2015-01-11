package com.disdik.promote.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.disdik.promote.Application;
import com.disdik.promote.domain.Unit;
import com.disdik.promote.repository.UnitRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UnitResource REST controller.
 *
 * @see UnitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UnitResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    

    @Inject
    private UnitRepository unitRepository;

    private MockMvc restUnitMockMvc;

    private Unit unit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UnitResource unitResource = new UnitResource();
        ReflectionTestUtils.setField(unitResource, "unitRepository", unitRepository);
        this.restUnitMockMvc = MockMvcBuilders.standaloneSetup(unitResource).build();
    }

    @Before
    public void initTest() {
        unit = new Unit();
        unit.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUnit() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);

        // Create the Unit
        restUnitMockMvc.perform(post("/app/rest/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(1);
        Unit testUnit = units.iterator().next();
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the units
        restUnitMockMvc.perform(get("/app/rest/units"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(unit.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/app/rest/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/app/rest/units/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Update the unit
        unit.setName(UPDATED_NAME);
        restUnitMockMvc.perform(post("/app/rest/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(1);
        Unit testUnit = units.iterator().next();
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(delete("/app/rest/units/{id}", unit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }
}
