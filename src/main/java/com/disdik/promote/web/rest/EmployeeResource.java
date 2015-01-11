package com.disdik.promote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.disdik.promote.domain.Employee;
import com.disdik.promote.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/app")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * POST  /rest/employees -> Create a new employee.
     */
    @RequestMapping(value = "/rest/employees",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Employee employee) {
        log.debug("REST request to save Employee : {}", employee);
        employeeRepository.save(employee);
    }

    /**
     * GET  /rest/employees -> get all the employees.
     */
    @RequestMapping(value = "/rest/employees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Employee> getAll() {
        log.debug("REST request to get all Employees");
        return employeeRepository.findAll();
    }

    /**
     * GET  /rest/employees/:id -> get the "id" employee.
     */
    @RequestMapping(value = "/rest/employees/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        return Optional.ofNullable(employeeRepository.findOne(id))
            .map(employee -> new ResponseEntity<>(
                employee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/employees/:id -> delete the "id" employee.
     */
    @RequestMapping(value = "/rest/employees/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeRepository.delete(id);
    }
}
