package com.bits.hr.web.rest.employmentActions;

import com.bits.hr.domain.User;
import com.bits.hr.domain.enumeration.EventType;
import com.bits.hr.domain.enumeration.RequestMethod;
import com.bits.hr.errors.BadRequestAlertException;
import com.bits.hr.service.EmploymentHistoryService;
import com.bits.hr.service.EventLog.EventLoggingPublisher;
import com.bits.hr.service.config.CurrentEmployeeService;
import com.bits.hr.service.dto.EmploymentHistoryDTO;
import com.bits.hr.service.employmentHistory.IncrementService;
import com.bits.hr.service.event.FestivalBonusReGenerationEvent;
import com.bits.hr.service.importXL.batchOperations.employmentActions.BatchOperationsViaXlsx;
import com.bits.hr.web.rest.EmploymentHistoryResource;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing increment operations
 */
@RestController
@RequestMapping("/api/employee-mgt")
public class IncrementResource {

    private static final String ENTITY_NAME = "increment";

    private static final String RESOURCE_NAME = "IncrementResource";
    private final Logger log = LoggerFactory.getLogger(EmploymentHistoryResource.class);
    private final EmploymentHistoryService employmentHistoryService;
    private final IncrementService incrementService;
    private final BatchOperationsViaXlsx batchOperationsViaXlsx;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final CurrentEmployeeService currentEmployeeService;
    private final EventLoggingPublisher eventLoggingPublisher;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public IncrementResource(
        EmploymentHistoryService employmentHistoryService,
        IncrementService incrementService,
        @Qualifier("Increment") BatchOperationsViaXlsx batchOperationsViaXlsx,
        ApplicationEventPublisher applicationEventPublisher,
        CurrentEmployeeService currentEmployeeService,
        EventLoggingPublisher eventLoggingPublisher
    ) {
        this.employmentHistoryService = employmentHistoryService;
        this.incrementService = incrementService;
        this.batchOperationsViaXlsx = batchOperationsViaXlsx;
        this.applicationEventPublisher = applicationEventPublisher;
        this.currentEmployeeService = currentEmployeeService;
        this.eventLoggingPublisher = eventLoggingPublisher;
    }

    /**
     * {@code POST  /increment} : Create a new promotion actions.
     * employmentHistory emtity will keep track of every employment actions.
     *
     * @param employmentHistoryDTO the employmentHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentHistoryDTO, or with status {@code 400 (Bad Request)} if the employmentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/increment")
    public ResponseEntity<EmploymentHistoryDTO> createEmploymentHistory(@RequestBody EmploymentHistoryDTO employmentHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to create new increment");
        if (employmentHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new employmentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentHistoryDTO result = incrementService.createIncrement(employmentHistoryDTO);
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, result, RequestMethod.POST, RESOURCE_NAME);

        // publish event for upcoming festival generation
        publishFestivalBonusReGenerationEvent(result.getEffectiveDate()); //todo: date check

        return ResponseEntity
            .created(new URI("/api/employee-mgt/increment" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-histories} : Updates an existing employmentHistory.
     *
     * @param employmentHistoryDTO the employmentHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the employmentHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/increment")
    public ResponseEntity<EmploymentHistoryDTO> updateEmploymentHistory(@RequestBody EmploymentHistoryDTO employmentHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to update EmploymentHistory : {}", employmentHistoryDTO);
        if (employmentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        EmploymentHistoryDTO result = incrementService.updateIncrement(employmentHistoryDTO);
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, result, RequestMethod.PUT, RESOURCE_NAME);
        // publish event for upcoming festival generation
        publishFestivalBonusReGenerationEvent(result.getEffectiveDate());
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employmentHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /employment-histories/:id} : delete the "id" employmentHistory.
     *
     * @param id the id of the employmentHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/increment/{id}")
    public ResponseEntity<Void> deleteEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentHistory : {}", id);
        EmploymentHistoryDTO employmentHistoryDTO = incrementService.deleteAndRevertIncrement(id);
        // publish event for upcoming festival generation
        publishFestivalBonusReGenerationEvent(LocalDate.now()); //todo: check date
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, employmentHistoryDTO, RequestMethod.DELETE, RESOURCE_NAME);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /employment-histories} : get all the employmentHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentHistories in body.
     */

    @GetMapping("/increment")
    public ResponseEntity<List<EmploymentHistoryDTO>> getAllPromotions(
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all increment");
        Page<EmploymentHistoryDTO> page = employmentHistoryService.findAll(EventType.INCREMENT, employeeId, startDate, endDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, Optional.empty(), RequestMethod.GET, RESOURCE_NAME);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employment-histories/:id} : get the "id" employmentHistory.
     *
     * @param id the id of the employmentHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/increment/{id}")
    public ResponseEntity<EmploymentHistoryDTO> getEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to get Increment : {}", id);
        Optional<EmploymentHistoryDTO> employmentHistoryDTO = employmentHistoryService.findOne(id);
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, employmentHistoryDTO, RequestMethod.GET, RESOURCE_NAME);
        return ResponseUtil.wrapOrNotFound(employmentHistoryDTO);
    }

    @PostMapping("/batch-increment")
    public ResponseEntity<Boolean> upload(@RequestParam("file") MultipartFile file) throws Exception {
        boolean hasDone = batchOperationsViaXlsx.batchOperations(file);
        User user = currentEmployeeService.getCurrentUser().get();
        eventLoggingPublisher.publishEvent(user, hasDone, RequestMethod.POST, RESOURCE_NAME);
        return ResponseEntity.ok(hasDone);
    }

    private void publishFestivalBonusReGenerationEvent(LocalDate date) {
        log.debug("publishing Festival Bonus Re Generation");
        FestivalBonusReGenerationEvent festivalBonusReGenerationEvent = new FestivalBonusReGenerationEvent(true, date);
        applicationEventPublisher.publishEvent(festivalBonusReGenerationEvent);
    }
}