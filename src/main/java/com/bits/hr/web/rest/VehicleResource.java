package com.bits.hr.web.rest;

import com.bits.hr.errors.BadRequestAlertException;
import com.bits.hr.service.VehicleService;
import com.bits.hr.service.dto.VehicleDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bits.hr.domain.Vehicle}.
 */
@RestController
@RequestMapping("/api/employee-mgt")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);

    private static final String ENTITY_NAME = "vehicle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleService vehicleService;

    public VehicleResource(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * {@code POST  /vehicles} : Create a new vehicle.
     *
     * @param vehicleDTO the vehicleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleDTO, or with status {@code 400 (Bad Request)} if the vehicle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicleDTO);
        if (vehicleDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity
            .created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicles} : Updates an existing vehicle.
     *
     * @param vehicleDTO the vehicleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicles")
    public ResponseEntity<VehicleDTO> updateVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        log.debug("REST request to update Vehicle : {}", vehicleDTO);
        if (vehicleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicles} : get all the vehicles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Vehicles");
        Page<VehicleDTO> page = vehicleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicles/:id} : get the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        Optional<VehicleDTO> vehicleDTO = vehicleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleDTO);
    }

    /**
     * {@code DELETE  /vehicles/:id} : delete the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
