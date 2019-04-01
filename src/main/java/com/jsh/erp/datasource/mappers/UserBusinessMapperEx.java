package com.jsh.erp.datasource.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.Date; /**
 * Description
 *
 * @Author: qiankunpingtai
 * @Date: 2019/3/29 15:09
 */
public interface UserBusinessMapperEx {
    int batchDeleteUserBusinessByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}
