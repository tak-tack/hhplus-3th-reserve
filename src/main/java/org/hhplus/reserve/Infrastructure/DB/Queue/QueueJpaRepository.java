package org.hhplus.reserve.Infrastructure.DB.Queue;

import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Integer> {

    @Query("SELECT q FROM QueueEntity q WHERE q.userId = :userId AND q.queueStatus = :queueStatus")
    Optional<List<QueueEntity>> findByWaitingUserId(@Param("userId") Integer userId, @Param("queueStatus")QueueStatus queueStatus);

    @Query("SELECT q FROM QueueEntity q WHERE q.userId = :userId")
    List<QueueEntity> findByUserId(@Param("userId") Integer userId);

    // 마지막 큐데이터(행)
    @Query("SELECT createDt FROM QueueEntity WHERE queueStatus = :queueStatus ORDER BY createDt DESC LIMIT 1")
    Optional<String> findLastQueue(@Param("queueStatus") QueueStatus queueStatus);

    // 대기중인 큐데이터
    @Query("SELECT COUNT(*) FROM QueueEntity WHERE createDt < :lastQueueDate")
    Optional<Integer> countWaitingQueue(@Param("lastQueueDate") String lastQueueDate);

    // 큐상태 유저 조회
    @Query("SELECT qe2.queueId FROM  QueueEntity qe2 WHERE qe2.queueStatus = :currentStatus ORDER BY qe2.createDt LIMIT 50")
    List<Integer> findQueueIdsByStatus(@Param("currentStatus") QueueStatus currentStatus);

    @Modifying
    @Query(value="INSERT into dba.queue (user_Id ,queue_Status, create_Dt, modify_Dt) values " +
            "(:userId, :queueStatus, :createDt ,null)",nativeQuery = true)
    void register(@Param("userId") Integer userId,
                        @Param("createDt") String createDt,
                        @Param("queueStatus")String queueStatus
    );

    // 큐 데이터 상태 변경
    @Modifying
    @Query(value="UPDATE dba.queue qe1 SET qe1.modify_Dt = :newModifyDt, qe1.queue_Status = :newStatus WHERE qe1.queue_Id IN :queueIds" , nativeQuery = true)
    void updateQueueStatusByIds(@Param("newModifyDt") String newModifyDt, @Param("newStatus") QueueStatus newStatus, @Param("queueIds") List<Integer> queueIds);

}