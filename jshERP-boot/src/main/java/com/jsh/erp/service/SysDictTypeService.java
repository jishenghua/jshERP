package com.jsh.erp.service;

import com.alibaba.fastjson.JSONArray;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DepotExample;
import com.jsh.erp.datasource.entities.SysDictData;
import com.jsh.erp.datasource.entities.SysDictType;
import com.jsh.erp.datasource.mappers.SysDictDataMapper;
import com.jsh.erp.datasource.mappers.SysDictTypeMapper;
import com.jsh.erp.exception.BusinessRunTimeException;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 * @author jishenghua
 */
@Service
public class SysDictTypeService {

    private Logger logger = LoggerFactory.getLogger(SysDictTypeService.class);

    @Resource
    private LogService logService;

    @Resource
    private SysDictTypeMapper dictTypeMapper;

    @Resource
    private SysDictDataMapper dictDataMapper;

    @Resource
    private RedisService redisService;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType)
    {
        PageUtils.startPage();
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    public List<SysDictType> selectDictTypeAll()
    {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (dictDatas!=null && !dictDatas.isEmpty()) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (dictDatas!=null && !dictDatas.isEmpty()) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    public SysDictType selectDictTypeById(Long dictId)
    {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    public SysDictType selectDictTypeByType(String dictType)
    {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 加载字典缓存数据
     */
    public void loadingDictCache()
    {
        SysDictData dictData = new SysDictData();
        dictData.setStatus("0");
        Map<String, List<SysDictData>> dictDataMap = dictDataMapper.selectDictDataList(dictData).stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        for (Map.Entry<String, List<SysDictData>> entry : dictDataMap.entrySet())
        {
            DictUtils.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList()));
        }
    }

    /**
     * 清空字典缓存数据
     */
    public void clearDictCache()
    {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    public void resetDictCache()
    {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    public int insertDictType(SysDictType dict)
    {
        int row = dictTypeMapper.insertDictType(dict);
        if (row > 0)
        {
            DictUtils.setDictCache(dict.getDictType(), null);
        }
        return row;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Transactional
    public int updateDictType(SysDictType dict)
    {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = dictTypeMapper.updateDictType(dict);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    public boolean checkDictTypeUnique(SysDictType dict)
    {
        Long dictId = StringUtil.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtil.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue())
        {
            return false;
        }
        return true;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDictType(Long id, HttpServletRequest request) throws Exception {
        return batchDeleteDictTypeByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDictType(String ids, HttpServletRequest request) throws Exception {
        return batchDeleteDictTypeByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDictTypeByIds(String ids)throws Exception {
        int result=0;
        String [] idArray=ids.split(",");
        List<String> dictTypeList = new ArrayList<>();
        for (String dictId : idArray) {
            SysDictType dictType = selectDictTypeById(Long.valueOf(dictId));
            dictTypeList.add(dictType.getDictType());
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BusinessRunTimeException(ExceptionConstants.DICT_TYPE_ALREADY_USED_CODE,
                        String.format(ExceptionConstants.DICT_TYPE_ALREADY_USED_MSG, dictType.getDictName()));
            }
        }
        try {
            //记录日志
            StringBuffer sb = new StringBuffer();
            sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
            List<SysDictType> list = getDictTypeListByIds(ids);
            for(SysDictType sysDictType: list){
                sb.append("[").append(sysDictType.getDictName()).append("]");
            }
            result = dictTypeMapper.batchDeleteDictTypeByIds(idArray);
            for (String dictType : dictTypeList) {
                DictUtils.removeDictCache(dictType);
            }
            //记录日志
            logService.insertLog("字典类型", sb.toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } catch (Exception e) {
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<SysDictType> getDictTypeListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        return dictTypeMapper.getDictTypeListByIds(idList);
    }
}
