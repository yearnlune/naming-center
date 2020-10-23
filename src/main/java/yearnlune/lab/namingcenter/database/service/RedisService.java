package yearnlune.lab.namingcenter.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import yearnlune.lab.namingcenter.config.RedisConfig;
import yearnlune.lab.namingcenter.database.table.Naming;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;
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

    private final NamingService namingService;

    private Timestamp previousTime;

    private final TimerTask autoCompleteTask = new TimerTask() {
        @Override
        public void run() {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            List<Naming> updatedNamingList = namingService.findAllUpdatedNaming(previousTime);
            for (Naming naming : updatedNamingList) {
                redisTemplate.opsForValue().set(makeNameRedisKey(naming.getName()), 0);
            }
            previousTime = currentTime;
        }
    };

    public RedisService(RedisTemplate<String, Integer> redisTemplate, NamingService namingService) {
        this.redisTemplate = redisTemplate;
        this.namingService = namingService;
    }

    @PostConstruct
    public void init() {
        previousTime = new Timestamp(System.currentTimeMillis());
        List<String> namingList = namingService.findNames();
        for (String naming : namingList) {
            redisTemplate.opsForValue().set(makeNameRedisKey(naming), 0);
        }
    }

    public void initializeLoginFailCount(String loginId) {
        String redisKey = makeLoginFailRedisKey(loginId);
        redisTemplate.opsForValue().set(redisKey, INIT_LOGIN_FAIL_COUNT);
    }

    public void increaseLoginFailCount(String loginId) {
        String redisKey = makeLoginFailRedisKey(loginId);
        Integer loginFailCount = getLoginFailCountIfExist(redisKey);
        Integer increasedLoginFailCount = loginFailCount + 1;

        if (increasedLoginFailCount.intValue() == MAX_LOGIN_FAIL_COUNT) {
            redisTemplate.opsForValue().set(redisKey, increasedLoginFailCount, MAX_LOGIN_LOCKED_SECONDS, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(redisKey, increasedLoginFailCount);
        }
    }

    public boolean isLoginLock(String loginId) {
        String redisKey = makeLoginFailRedisKey(loginId);
        Integer loginFailCount = getLoginFailCountIfExist(redisKey);

        return loginFailCount >= MAX_LOGIN_FAIL_COUNT;
    }

    private String makeLoginFailRedisKey(String loginId) {
        return RedisConfig.REDIS_ACCOUNT + ":" + RedisConfig.REDIS_LOGIN_FAILED + ":" + loginId;
    }

    public static String makeNameRedisKey(String name) {
        return RedisConfig.REDIS_NAME + ":" + name;
    }

    private Integer getLoginFailCountIfExist(String redisKey) {
        Integer loginFailCount = redisTemplate.opsForValue().get(redisKey);

        if (loginFailCount == null) {
            loginFailCount = 0;
        }
        return loginFailCount;
    }
}
