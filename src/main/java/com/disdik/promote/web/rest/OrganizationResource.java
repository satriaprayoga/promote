package com.disdik.promote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.disdik.promote.domain.Organization;
import com.disdik.promote.repository.OrganizationRepository;
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
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/app")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    @Inject
    private OrganizationRepository organizationRepository;

    /**
     * POST  /rest/organizations -> Create a new organization.
     */
    @RequestMapping(value = "/rest/organizations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Organization organization) {
        log.debug("REST request to save Organization : {}", organization);
        organizationRepository.save(organization);
    }

    /**
     * GET  /rest/organizations -> get all the organizations.
     */
    @RequestMapping(value = "/rest/organizations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Organization> getAll() {
        log.debug("REST request to get all Organizations");
        return organizationRepository.findAll();
    }

    /**
     * GET  /rest/organizations/:id -> get the "id" organization.
     */
    @RequestMapping(value = "/rest/organizations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Organization> get(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        return Optional.ofNullable(organizationRepository.findOne(id))
            .map(organization -> new ResponseEntity<>(
                organization,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/organizations/:id -> delete the "id" organization.
     */
    @RequestMapping(value = "/rest/organizations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }
}
