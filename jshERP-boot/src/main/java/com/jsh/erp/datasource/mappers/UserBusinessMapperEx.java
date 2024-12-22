package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.UserBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: qiankunpingtai
 * @Date: 2019/3/29 15:09
 */
public interface UserBusinessMapperEx {

    int batchDeleteUserBusinessByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<UserBusiness> getBasicDataByKeyIdAndType(
            @Param("keyId") String keyId,
            @Param("type") String type);

    void updateValueByTypeAndKeyId(@Param("type") String type, @Param("keyId") String keyId, @Param("ubValue") String ubValue);
}
