package com.emcloud.nfs.service;

import com.emcloud.nfs.domain.MessageTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MessageTemplate.
 */
public interface MessageTemplateService {

    /**
     * Save a messageTemplate.
     *
     * @param messageTemplate the entity to save
     * @return the persisted entity
     */
    MessageTemplate save(MessageTemplate messageTemplate);

    /**
     *  Get all the messageTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MessageTemplate> findAll(Pageable pageable);

    /**
     *  Get the "id" messageTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MessageTemplate findOne(Long id);

    /**
     *  Delete the "id" messageTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
