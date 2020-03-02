package yearnlune.lab.namingcenter.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import yearnlune.lab.namingcenter.config.RedisConfig;

import java.util.concurrent.TimeUnit;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.02
 * DESCRIPTION :
 */

@Slf4j
@Service
public class RedisService {
    private static final Integer INIT_LOGIN_FAIL_COUNT = 0;

    private static final Integer MAX_LOGIN_FAIL_COUNT = 5;

    private static final Integer MAX_LOGIN_LOCKED_SECONDS = 180;

    private final RedisTemplate<String, Integer> redisTemplate;

    public RedisService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void initializeLoginFailCount(String loginId) {
        String redisKey = makeRedisKey(loginId);
        redisTemplate.opsForValue().set(redisKey, INIT_LOGIN_FAIL_COUNT);
    }

    public void increaseLoginFailCount(String loginId) {
        String redisKey = makeRedisKey(loginId);
        Integer loginFailCount = getLoginFailCountIfExist(redisKey);
        Integer increasedLoginFailCount = loginFailCount + 1;

        if (increasedLoginFailCount.intValue() == MAX_LOGIN_FAIL_COUNT) {
            redisTemplate.opsForValue().set(redisKey, increasedLoginFailCount, MAX_LOGIN_LOCKED_SECONDS, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(redisKey, increasedLoginFailCount);
        }
    }

    public boolean isLoginLock(String loginId) {
        String redisKey = makeRedisKey(loginId);
        Integer loginFailCount = getLoginFailCountIfExist(redisKey);

        if (loginFailCount < MAX_LOGIN_FAIL_COUNT) {
            return false;
        }
        return true;
    }

    private String makeRedisKey(String loginId) {
        return RedisConfig.REDIS_ACCOUNT + ":" + RedisConfig.REDIS_LOGIN_FAILED + ":" + loginId;
    }

    private Integer getLoginFailCountIfExist(String redisKey) {
        Integer loginFailCount = redisTemplate.opsForValue().get(redisKey);

        if (loginFailCount == null) {
            loginFailCount = 0;
        }
        return loginFailCount;
    }
}
