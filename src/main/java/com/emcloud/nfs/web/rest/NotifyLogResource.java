package com.emcloud.nfs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.nfs.domain.NotifyLog;
import com.emcloud.nfs.service.NotifyLogService;
import com.emcloud.nfs.web.rest.errors.BadRequestAlertException;
import com.emcloud.nfs.web.rest.util.HeaderUtil;
import com.emcloud.nfs.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NotifyLog.
 */
@RestController
@RequestMapping("/api")
public class NotifyLogResource {

    private final Logger log = LoggerFactory.getLogger(NotifyLogResource.class);

    private static final String ENTITY_NAME = "notifyLog";

    private final NotifyLogService notifyLogService;

    public NotifyLogResource(NotifyLogService notifyLogService) {
        this.notifyLogService = notifyLogService;
    }

    /**
     * POST  /notify-logs : Create a new notifyLog.
     *
     * @param notifyLog the notifyLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notifyLog, or with status 400 (Bad Request) if the notifyLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notify-logs")
    @Timed
    public ResponseEntity<NotifyLog> createNotifyLog(@Valid @RequestBody NotifyLog notifyLog) throws URISyntaxException {
        log.debug("REST request to save NotifyLog : {}", notifyLog);
        if (notifyLog.getId() != null) {
            throw new BadRequestAlertException("A new notifyLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotifyLog result = notifyLogService.save(notifyLog);
        return ResponseEntity.created(new URI("/api/notify-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notify-logs : Updates an existing notifyLog.
     *
     * @param notifyLog the notifyLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notifyLog,
     * or with status 400 (Bad Request) if the notifyLog is not valid,
     * or with status 500 (Internal Server Error) if the notifyLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notify-logs")
    @Timed
    public ResponseEntity<NotifyLog> updateNotifyLog(@Valid @RequestBody NotifyLog notifyLog) throws URISyntaxException {
        log.debug("REST request to update NotifyLog : {}", notifyLog);
        if (notifyLog.getId() == null) {
            return createNotifyLog(notifyLog);
        }
        NotifyLog result = notifyLogService.save(notifyLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notifyLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notify-logs : get all the notifyLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notifyLogs in body
     */
    @GetMapping("/notify-logs")
    @Timed
    public ResponseEntity<List<NotifyLog>> getAllNotifyLogs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NotifyLogs");
        Page<NotifyLog> page = notifyLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notify-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notify-logs/:id : get the "id" notifyLog.
     *
     * @param id the id of the notifyLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notifyLog, or with status 404 (Not Found)
     */
    @GetMapping("/notify-logs/{id}")
    @Timed
    public ResponseEntity<NotifyLog> getNotifyLog(@PathVariable Long id) {
        log.debug("REST request to get NotifyLog : {}", id);
        NotifyLog notifyLog = notifyLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notifyLog));
    }

    /**
     * DELETE  /notify-logs/:id : delete the "id" notifyLog.
     *
     * @param id the id of the notifyLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notify-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotifyLog(@PathVariable Long id) {
        log.debug("REST request to delete NotifyLog : {}", id);
        notifyLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
