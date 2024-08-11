//package org.hhplus.reserve.Business.Usecase;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Duration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.awaitility.Awaitility.await;
//import static org.junit.jupiter.api.Assertions.*;
//
////@Slf4j
//@SpringBootTest
//class RedisUtilTest {
//    final String KEY = "key";
//    final String VALUE = "value";
//    final Duration DURATION = Duration.ofMillis(5000);
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @BeforeEach
//    void shutDown() {
//        redisUtil.setValues(KEY, VALUE, DURATION);
//    }
//
//    @AfterEach
//    void tearDown() {
//        redisUtil.deleteValues(KEY);
//    }
//
//    @Test
//    @DisplayName("Redis 테스트 - 저장")
//    void saveAndFindTest() throws Exception {
//        // when
//        String findValue = redisUtil.getValues(KEY);
//
//        // then
//        assertThat(VALUE).isEqualTo(findValue);
//    }
//
//    @Test
//    @DisplayName("Redis 테스트 - 수정")
//    void updateTest() throws Exception {
//        // given
//        String updateValue = "updateValue";
//        redisUtil.setValues(KEY, updateValue, DURATION);
//
//        // when
//        String findValue = redisUtil.getValues(KEY);
//
//        // then
//        assertThat(updateValue).isEqualTo(findValue);
//        assertThat(VALUE).isNotEqualTo(findValue);
//    }
//
//    @Test
//    @DisplayName("Redis 테스트 - 삭제")
//    void deleteTest() throws Exception {
//        // when
//        redisUtil.deleteValues(KEY);
//        String findValue = redisUtil.getValues(KEY);
//
//        // then
//        assertThat(findValue).isEqualTo("false");
//    }
//
//    @Test
//    @DisplayName("Redis 테스트 - 만료시간후 삭제")
//    void expiredTest() throws Exception {
//        // when
//        String findValue = redisUtil.getValues(KEY);
//        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
//                () -> {
//                    String expiredValue = redisUtil.getValues(KEY);
//                    assertThat(expiredValue).isNotEqualTo(findValue);
//                    assertThat(expiredValue).isEqualTo("false");
//                }
//        );
//    }
//}