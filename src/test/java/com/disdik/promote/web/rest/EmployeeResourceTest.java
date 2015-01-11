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
import org.joda.time.LocalDate;
import java.util.List;

import com.disdik.promote.Application;
import com.disdik.promote.domain.Employee;
import com.disdik.promote.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EmployeeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_NIP = "SAMPLE_TEXT";
    private static final String UPDATED_NIP = "UPDATED_TEXT";
    
    private static final LocalDate DEFAULT_BIRTH_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = new LocalDate();
    
    private static final String DEFAULT_BIRTH_PLACE = "SAMPLE_TEXT";
    private static final String UPDATED_BIRTH_PLACE = "UPDATED_TEXT";
    
    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    

    @Inject
    private EmployeeRepository employeeRepository;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource();
        ReflectionTestUtils.setField(employeeResource, "employeeRepository", employeeRepository);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource).build();
    }

    @Before
    public void initTest() {
        employee = new Employee();
        employee.setName(DEFAULT_NAME);
        employee.setNip(DEFAULT_NIP);
        employee.setBirthDate(DEFAULT_BIRTH_DATE);
        employee.setBirthPlace(DEFAULT_BIRTH_PLACE);
        employee.setTitle(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        // Validate the database is empty
        assertThat(employeeRepository.findAll()).hasSize(0);

        // Create the Employee
        restEmployeeMockMvc.perform(post("/app/rest/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);
        Employee testEmployee = employees.iterator().next();
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getNip()).isEqualTo(DEFAULT_NIP);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testEmployee.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employees
        restEmployeeMockMvc.perform(get("/app/rest/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(employee.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].nip").value(DEFAULT_NIP.toString()))
                .andExpect(jsonPath("$.[0].birthDate").value(DEFAULT_BIRTH_DATE.toString()))
                .andExpect(jsonPath("$.[0].birthPlace").value(DEFAULT_BIRTH_PLACE.toString()))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/app/rest/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nip").value(DEFAULT_NIP.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/app/rest/employees/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Update the employee
        employee.setName(UPDATED_NAME);
        employee.setNip(UPDATED_NIP);
        employee.setBirthDate(UPDATED_BIRTH_DATE);
        employee.setBirthPlace(UPDATED_BIRTH_PLACE);
        employee.setTitle(UPDATED_TITLE);
        restEmployeeMockMvc.perform(post("/app/rest/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);
        Employee testEmployee = employees.iterator().next();
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getNip()).isEqualTo(UPDATED_NIP);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testEmployee.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(delete("/app/rest/employees/{id}", employee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(0);
    }
}
