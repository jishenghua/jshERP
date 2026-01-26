package com.jsh.erp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String UNLOCK_LUA_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "    return redis.call('del', KEYS[1]) " +
                    "else " +
                    "    return 0 " +
                    "end";

    private static final String LOCK_LUA_SCRIPT =
            "local key = KEYS[1] " +
                    "local value = ARGV[1] " +
                    "local expire = ARGV[2] " +
                    "local result = redis.call('setnx', key, value) " +
                    "if result == 1 then " +
                    "    redis.call('pexpire', key, expire) " +
                    "    return 1 " +
                    "else " +
                    "    local currentValue = redis.call('get', key) " +
                    "    if currentValue == value then " +
                    "        redis.call('pexpire', key, expire) " +
                    "        return 1 " +
                    "    else " +
                    "        return 0 " +
                    "    end " +
                    "end";

    /**
     * 尝试获取分布式锁（支持锁重入）
     * @param lockKey 锁的key
     * @param requestId 请求ID（可使用UUID）
     * @param expireMillis 锁的过期时间（毫秒）
     * @param waitMillis 等待时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId,
                           long expireMillis, long waitMillis)
            throws InterruptedException {

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < waitMillis) {
            // 使用Lua脚本保证原子性
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptText(LOCK_LUA_SCRIPT);
            script.setResultType(Long.class);

            Long result = redisTemplate.execute(
                    script,
                    Collections.singletonList(lockKey),
                    requestId,
                    String.valueOf(expireMillis)
            );

            if (result != null && result == 1L) {
                return true; // 获取锁成功
            }

            // 短暂休眠后重试
            Thread.sleep(10);
        }

        return false; // 获取锁失败
    }

    /**
     * 释放分布式锁
     */
    public boolean unlock(String lockKey, String requestId) {
        try {
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptText(UNLOCK_LUA_SCRIPT);
            script.setResultType(Long.class);

            Long result = redisTemplate.execute(
                    script,
                    Collections.singletonList(lockKey),
                    requestId
            );

            return result != null && result == 1L;
        } catch (Exception e) {
            log.error("释放锁失败, lockKey: {}", lockKey, e);
            return false;
        }
    }

    /**
     * 快速获取锁（立即返回，不等待）
     */
    public boolean lockFast(String lockKey, String requestId, long expireMillis) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(LOCK_LUA_SCRIPT);
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(
                script,
                Collections.singletonList(lockKey),
                requestId,
                String.valueOf(expireMillis)
        );

        return result != null && result == 1L;
    }
}
