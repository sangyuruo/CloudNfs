package com.emcloud.nfs.service.impl;

import com.emcloud.nfs.security.SecurityUtils;
import com.emcloud.nfs.service.MessageTemplateService;
import com.emcloud.nfs.domain.MessageTemplate;
import com.emcloud.nfs.repository.MessageTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing MessageTemplate.
 */
@Service
@Transactional
public class MessageTemplateServiceImpl implements MessageTemplateService{

    private final Logger log = LoggerFactory.getLogger(MessageTemplateServiceImpl.class);

    private final MessageTemplateRepository messageTemplateRepository;

    public MessageTemplateServiceImpl(MessageTemplateRepository messageTemplateRepository) {
        this.messageTemplateRepository = messageTemplateRepository;
    }

    /**
     * Save a messageTemplate.
     *
     * @param messageTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public MessageTemplate save(MessageTemplate messageTemplate) {
        log.debug("Request to save MessageTemplate : {}", messageTemplate);
        messageTemplate.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        messageTemplate.setCreateTime(Instant.now());
        messageTemplate.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        messageTemplate.setUpdateTime(Instant.now());
        return messageTemplateRepository.save(messageTemplate);
    }

    /**
     * Update a messageTemplate.
     *
     * @param messageTemplate the entity to update
     * @return the persisted entity
     */
    @Override
    public MessageTemplate update(MessageTemplate messageTemplate) {
        log.debug("Request to save MessageTemplate : {}", messageTemplate);
        messageTemplate.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        messageTemplate.setUpdateTime(Instant.now());
        return messageTemplateRepository.save(messageTemplate);
    }

    /**
     *  Get all the messageTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MessageTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all MessageTemplates");
        return messageTemplateRepository.findAll(pageable);
    }

    /**
     *  Get one messageTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MessageTemplate findOne(Long id) {
        log.debug("Request to get MessageTemplate : {}", id);
        return messageTemplateRepository.findOne(id);
    }

    /**
     *  Delete the  messageTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageTemplate : {}", id);
        messageTemplateRepository.delete(id);
    }
}
