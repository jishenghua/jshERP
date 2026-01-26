package com.jsh.erp.service;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.SerialNumber;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import com.jsh.erp.datasource.mappers.SequenceMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.utils.RedisLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Description
 *
 * @Author: jishenghua
 * @Date: 2021/3/16 16:33
 */
@Service
public class SequenceService {
    private Logger logger = LoggerFactory.getLogger(SequenceService.class);

    @Resource
    private SequenceMapperEx sequenceMapperEx;

    @Resource
    private RedisLockUtil redisLockUtil;

    private static final long LOCK_EXPIRE_TIME = 3000; // 锁有效期3秒
    private static final long LOCK_WAIT_TIME = 100;    // 等待锁时间100ms

    public SerialNumber getSequence(long id)throws Exception {
        return null;
    }

    public List<SerialNumberEx> select(String name, Integer offset, Integer rows)throws Exception {
        return null;
    }

    public Long countSequence(String name)throws Exception {
        return null;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSequence(JSONObject obj, HttpServletRequest request)throws Exception {
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSequence(JSONObject obj, HttpServletRequest request) throws Exception{
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSequence(Long id, HttpServletRequest request)throws Exception {
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSequence(String ids, HttpServletRequest request)throws Exception {
        return 0;
    }

    public int checkIsNameExist(Long id, String serialNumber)throws Exception {
        return 0;
    }

    /**
     * 获取唯一单据编号
     */
    public String buildOnlyNumber() throws Exception {
        String lockKey = "sequence:lock:" + BusinessConstants.DEPOT_NUMBER_SEQ;
        String requestId = UUID.randomUUID().toString(); // 唯一请求ID
        boolean locked = false;
        try {
            // 尝试获取分布式锁
            locked = redisLockUtil.tryLock(
                    lockKey,
                    requestId,
                    LOCK_EXPIRE_TIME,
                    LOCK_WAIT_TIME
            );
            if (!locked) {
                throw new BusinessRunTimeException(ExceptionConstants.SEQUENCE_ONLY_FAILED_CODE, ExceptionConstants.SEQUENCE_ONLY_FAILED_MSG);
            }
            // 执行业务逻辑
            return doGenerateSequence();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessRunTimeException(ExceptionConstants.SEQUENCE_ONLY_BREAK_CODE, ExceptionConstants.SEQUENCE_ONLY_BREAK_MSG);
        } finally {
            // 释放锁
            if (locked) {
                redisLockUtil.unlock(lockKey, requestId);
            }
        }
    }

    /**
     * 实际生成单据编号
     */
    private String doGenerateSequence() throws Exception {
        try {
            // 执行数据库更新
            sequenceMapperEx.updateBuildOnlyNumber();
            Long number = sequenceMapperEx.getBuildOnlyNumber(BusinessConstants.DEPOT_NUMBER_SEQ);
            // 格式化返回
            return formatNumber(number);
        } catch (Exception e) {
            logger.error("生成单据编号失败", e);
            throw new BusinessRunTimeException(ExceptionConstants.SEQUENCE_ONLY_BREAK_CODE, ExceptionConstants.SEQUENCE_ONLY_BREAK_MSG);
        }
    }

    /**
     * 格式化数字
     */
    private String formatNumber(Long number) {
        if (number < BusinessConstants.SEQ_TO_STRING_MIN_LENGTH) {
            StringBuffer sb = new StringBuffer(number.toString());
            int len = BusinessConstants.SEQ_TO_STRING_MIN_LENGTH.toString().length() - sb.length();
            for (int i = 0; i < len; i++) {
                sb.insert(0, BusinessConstants.SEQ_TO_STRING_LESS_INSERT);
            }
            return sb.toString();
        } else {
            return number.toString();
        }
    }
}
