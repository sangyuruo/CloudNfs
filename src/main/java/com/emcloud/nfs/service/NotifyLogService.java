package com.emcloud.nfs.service;

import com.emcloud.nfs.domain.NotifyLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NotifyLog.
 */
public interface NotifyLogService {

    /**
     * Save a notifyLog.
     *
     * @param notifyLog the entity to save
     * @return the persisted entity
     */
    NotifyLog save(NotifyLog notifyLog);


    /**
     * Update a notifyLog.
     *
     * @param notifyLog the entity to update
     * @return the persisted entity
     */
    NotifyLog update(NotifyLog notifyLog);

    /**
     *  Get all the notifyLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NotifyLog> findAll(Pageable pageable);

    /**
     *  Get the "id" notifyLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NotifyLog findOne(Long id);

    /**
     *  Delete the "id" notifyLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
