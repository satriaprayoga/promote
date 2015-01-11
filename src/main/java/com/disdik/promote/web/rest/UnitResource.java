package com.disdik.promote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.disdik.promote.domain.Unit;
import com.disdik.promote.repository.UnitRepository;
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
 * REST controller for managing Unit.
 */
@RestController
@RequestMapping("/app")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    @Inject
    private UnitRepository unitRepository;

    /**
     * POST  /rest/units -> Create a new unit.
     */
    @RequestMapping(value = "/rest/units",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Unit unit) {
        log.debug("REST request to save Unit : {}", unit);
        unitRepository.save(unit);
    }

    /**
     * GET  /rest/units -> get all the units.
     */
    @RequestMapping(value = "/rest/units",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Unit> getAll() {
        log.debug("REST request to get all Units");
        return unitRepository.findAll();
    }

    /**
     * GET  /rest/units/:id -> get the "id" unit.
     */
    @RequestMapping(value = "/rest/units/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Unit> get(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        return Optional.ofNullable(unitRepository.findOne(id))
            .map(unit -> new ResponseEntity<>(
                unit,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/units/:id -> delete the "id" unit.
     */
    @RequestMapping(value = "/rest/units/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitRepository.delete(id);
    }
}
