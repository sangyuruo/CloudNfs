package com.emcloud.nfs.repository;

import com.emcloud.nfs.domain.MessageTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MessageTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {

}
