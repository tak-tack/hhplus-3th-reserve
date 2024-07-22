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
    Optional<List<QueueEntity>> findByWaitingUserId(@Param("userId") Integer userId, @Param("queueStatus")String queueStatus);

    @Query("SELECT q FROM QueueEntity q WHERE q.userId = :userId")
    List<QueueEntity> findByUserId(@Param("userId") Integer userId);

    // 마지막 큐데이터(행)
    @Query("SELECT createDt FROM QueueEntity WHERE queueStatus = :queueStatus ORDER BY createDt DESC LIMIT 1")
    Optional<String> findLastQueue(@Param("queueStatus") QueueStatus queueStatus);

    // 대기중인 큐데이터
    @Query("SELECT COUNT(*) FROM QueueEntity WHERE createDt > :lastQueueDate")
    Optional<Integer> countWaitingQueue(@Param("lastQueueDate") String lastQueueDate);

    // 큐데이터 상태 변경
    // 마지막 큐데이터의 lastQueueDate
    @Modifying
    @Query("UPDATE QueueEntity q SET q.modifyDt = :modifyDt, q.queueStatus = :queueStatus WHERE q.queueId IN " +
            "(SELECT q2.queueId FROM QueueEntity q2 WHERE q2.queueStatus = :queueStatus ORDER BY q2.createDt LIMIT 50)")
    void update(@Param("modifyDt") String modifyDt, @Param("queueStatus") QueueStatus queueStatus);

    @Modifying
    @Query(value="INSERT into dba.queue (user_Id ,queue_Status, create_Dt, modify_Dt) values " +
            "(:userId, :queueStatus, :createDt ,null)",nativeQuery = true)
    void register(@Param("userId") Integer userId,
                        @Param("createDt") String createDt,
                        @Param("queueStatus")String queueStatus
    );

}