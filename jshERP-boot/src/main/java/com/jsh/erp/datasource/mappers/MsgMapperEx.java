package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Msg;
import com.jsh.erp.datasource.entities.MsgEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgMapperEx {

    List<MsgEx> selectByConditionMsg(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByMsg(
            @Param("name") String name);

    int batchDeleteMsgByIds(@Param("ids") String ids[]);

    int insertSelectiveByTask(Msg record);

    int checkIsNameExistByTask(@Param("msgTitle") String msgTitle);

    Long getMsgCountByStatus(
            @Param("status") String status,
            @Param("userId") Long userId);
}