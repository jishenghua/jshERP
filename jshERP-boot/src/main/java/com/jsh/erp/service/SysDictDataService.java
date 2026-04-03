package com.jsh.erp.service;

import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.SysDictData;
import com.jsh.erp.datasource.mappers.SysDictDataMapper;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.DictUtils;
import com.jsh.erp.utils.PageUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author jishenghua
 */
@Service
public class SysDictDataService {

    private Logger logger = LoggerFactory.getLogger(SysDictDataService.class);

    @Resource
    private LogService logService;

    @Resource
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        PageUtils.startPage();
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    public void deleteDictDataByIds(Long[] dictCodes)
    {
        for (Long dictCode : dictCodes)
        {
            SysDictData data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    public int insertDictData(SysDictData data)
    {
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDictData(Long id, HttpServletRequest request) throws Exception {
        return batchDeleteDictDataByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDictData(String ids, HttpServletRequest request) throws Exception {
        return batchDeleteDictDataByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDictDataByIds(String ids)throws Exception {
        int result = 0;
        String[] idArray = ids.split(",");
        try {
            //记录日志
            String dictType = "";
            StringBuffer sb = new StringBuffer();
            sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
            List<SysDictData> list = getDictDataListByIds(ids);
            if(!list.isEmpty()) {
                dictType = list.get(0).getDictType();
                sb.append("字典：").append(dictType).append("下的数据：");
            }
            for(SysDictData sysDictData: list){
                sb.append("[").append(sysDictData.getDictLabel()).append("]");
            }
            result = dictDataMapper.batchDeleteDictDataByIds(idArray);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
            DictUtils.setDictCache(dictType, dictDatas);
            //记录日志
            logService.insertLog("字典数据", sb.toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } catch (Exception e) {
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<SysDictData> getDictDataListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        return dictDataMapper.getDictDataListByIds(idList);
    }
}
