package org.hhplus.reserve.Infrastructure.DB.Queue;

import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Integer> {

    //
    @Transactional
    @Query("SELECT queueId, queueStatus, userUUID, create_dt,modify_dt FROM QueueEntity WHERE userUUID = :userUUID AND queueStatus = :queueStatus")
    Optional<List<QueueEntity>> findByWaitingUuid(@Param("userUUID") UUID userUUID, @Param("queueStatus")QueueStatus queueStatus);

    @Transactional
    @Query("SELECT queueId, queueStatus, userUUID, create_dt,modify_dt FROM QueueEntity WHERE userUUID = :userUuid")
    Optional<List<QueueEntity>> findByUuid(@Param("userUuid") UUID userUuid);


    // 마지막 큐데이터(행)
    @Transactional
    @Query("SELECT create_dt FROM QueueEntity WHERE queueStatus = :queueStatus ORDER BY create_dt DESC LIMIT 1")
    Optional<String> findLastQueue(@Param("queueStatus")QueueStatus queueStatus);

    // 대기중인 큐데이터
    @Transactional
    @Query("SELECT COUNT(*) FROM QueueEntity WHERE create_dt > :lastQueueDate")
    Optional<Integer> countWaitingQueue(@Param("lastQueueDate") String lastQueueDate);

    // 큐데이터 상태 변경
    // 마지막 큐데이터의 lastQueueDate
    @Modifying
    @Transactional
    @Query("UPDATE QueueEntity q SET q.modify_dt = :modifyDt, q.queueStatus = :queueStatus WHERE q.queueId IN (SELECT q2.queueId FROM QueueEntity q2 WHERE q2.queueStatus = :queueStatus ORDER BY q2.create_dt LIMIT 50)")
    void updateQueue(@Param("modifyDt") String modifyDt, @Param("queueStatus") QueueStatus queueStatus);
    @Modifying
    @Transactional
    @Query(value="INSERT into dba.queue (useruuid ,queue_status, modify_dt) values (:userUuid, :queueStatus, null)",nativeQuery = true)
    void saveByUserUuid(@Param("userUuid") UUID userUuid,
                        @Param("queueStatus")QueueStatus queueStatus
    );


}