package com.emcloud.nfs.repository;

import com.emcloud.nfs.domain.NotifyLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NotifyLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotifyLogRepository extends JpaRepository<NotifyLog, Long> {

}
