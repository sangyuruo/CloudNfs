package com.emcloud.nfs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.nfs.domain.MessageTemplate;
import com.emcloud.nfs.service.MessageTemplateService;
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
 * REST controller for managing MessageTemplate.
 */
@RestController
@RequestMapping("/api")
public class MessageTemplateResource {

    private final Logger log = LoggerFactory.getLogger(MessageTemplateResource.class);

    private static final String ENTITY_NAME = "messageTemplate";

    private final MessageTemplateService messageTemplateService;

    public MessageTemplateResource(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    /**
     * POST  /message-templates : Create a new messageTemplate.
     *
     * @param messageTemplate the messageTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageTemplate, or with status 400 (Bad Request) if the messageTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-templates")
    @Timed
    public ResponseEntity<MessageTemplate> createMessageTemplate(@Valid @RequestBody MessageTemplate messageTemplate) throws URISyntaxException {
        log.debug("REST request to save MessageTemplate : {}", messageTemplate);
        if (messageTemplate.getId() != null) {
            throw new BadRequestAlertException("A new messageTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageTemplate result = messageTemplateService.save(messageTemplate);
        return ResponseEntity.created(new URI("/api/message-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-templates : Updates an existing messageTemplate.
     *
     * @param messageTemplate the messageTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageTemplate,
     * or with status 400 (Bad Request) if the messageTemplate is not valid,
     * or with status 500 (Internal Server Error) if the messageTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-templates")
    @Timed
    public ResponseEntity<MessageTemplate> updateMessageTemplate(@Valid @RequestBody MessageTemplate messageTemplate) throws URISyntaxException {
        log.debug("REST request to update MessageTemplate : {}", messageTemplate);
        if (messageTemplate.getId() == null) {
            return createMessageTemplate(messageTemplate);
        }
        MessageTemplate result = messageTemplateService.save(messageTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-templates : get all the messageTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messageTemplates in body
     */
    @GetMapping("/message-templates")
    @Timed
    public ResponseEntity<List<MessageTemplate>> getAllMessageTemplates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MessageTemplates");
        Page<MessageTemplate> page = messageTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/message-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /message-templates/:id : get the "id" messageTemplate.
     *
     * @param id the id of the messageTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/message-templates/{id}")
    @Timed
    public ResponseEntity<MessageTemplate> getMessageTemplate(@PathVariable Long id) {
        log.debug("REST request to get MessageTemplate : {}", id);
        MessageTemplate messageTemplate = messageTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageTemplate));
    }

    /**
     * DELETE  /message-templates/:id : delete the "id" messageTemplate.
     *
     * @param id the id of the messageTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageTemplate(@PathVariable Long id) {
        log.debug("REST request to delete MessageTemplate : {}", id);
        messageTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
