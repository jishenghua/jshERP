package com.jsh.erp.service.depotItem;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.DepotItemMapper;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class DepotItemService {
    private Logger logger = LoggerFactory.getLogger(DepotItemService.class);

    private final static String TYPE = "入库";
    private final static String SUM_TYPE = "Number";
    private final static String IN = "in";
    private final static String OUT = "out";

    @Resource
    private DepotItemMapper depotItemMapper;

    public DepotItem getDepotItem(long id) {
        return depotItemMapper.selectByPrimaryKey(id);
    }

    public List<DepotItem> getDepotItem() {
        DepotItemExample example = new DepotItemExample();
        return depotItemMapper.selectByExample(example);
    }

    public List<DepotItem> select(String name, Integer type, String remark, int offset, int rows) {
        return depotItemMapper.selectByConditionDepotItem(name, type, remark, offset, rows);
    }

    public int countDepotItem(String name, Integer type, String remark) {
        return depotItemMapper.countsByDepotItem(name, type, remark);
    }

    public int insertDepotItem(String beanJson, HttpServletRequest request) {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        return depotItemMapper.insertSelective(depotItem);
    }

    public int updateDepotItem(String beanJson, Long id) {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        depotItem.setId(id);
        return depotItemMapper.updateByPrimaryKeySelective(depotItem);
    }

    public int deleteDepotItem(Long id) {
        return depotItemMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteDepotItem(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdIn(idList);
        return depotItemMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<DepotItem> list = depotItemMapper.selectByExample(example);
        return list.size();
    }

    public List<DepotItemVo4HeaderId> getHeaderIdByMaterial(String materialParam, String depotIds) {
        return depotItemMapper.getHeaderIdByMaterial(materialParam, depotIds);
    }

    public List<DepotItemVo4DetailByTypeAndMId> findDetailByTypeAndMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapper.findDetailByTypeAndMaterialIdList(mId, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findDetailByTypeAndMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapper.findDetailByTypeAndMaterialIdCounts(mId);
    }

    public List<DepotItemVo4Material> findStockNumByMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapper.findStockNumByMaterialIdList(mId, monthTime, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findStockNumByMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapper.findStockNumByMaterialIdCounts(mId, monthTime);
    }

    public int insertDepotItemWithObj(DepotItem depotItem) {
        return depotItemMapper.insertSelective(depotItem);
    }

    public int updateDepotItemWithObj(DepotItem depotItem) {
        return depotItemMapper.updateByPrimaryKeySelective(depotItem);
    }

    public int findByTypeAndMaterialId(String type, Long mId) {
        if(type.equals(TYPE)) {
            return depotItemMapper.findByTypeAndMaterialIdIn(mId);
        } else {
            return depotItemMapper.findByTypeAndMaterialIdOut(mId);
        }
    }

    public List<DepotItemVo4WithInfoEx> getDetailList(Long headerId) {
        return depotItemMapper.getDetailList(headerId);
    }

    public List<DepotItemVo4WithInfoEx> findByAll(String headIds, String materialIds, Integer offset, Integer rows) {
        return depotItemMapper.findByAll(headIds, materialIds, offset, rows);
    }

    public int findByAllCount(String headIds, String materialIds) {
        return depotItemMapper.findByAllCount(headIds, materialIds);
    }

    public Double findByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapper.findByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapper.findByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public Double findPriceByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapper.findPriceByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findPriceByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapper.findPriceByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findPriceByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public Double buyOrSale(String type, String subType, Long MId, String MonthTime, String sumType) {
        if (SUM_TYPE.equals(sumType)) {
            return depotItemMapper.buyOrSaleNumber(type, subType, MId, MonthTime, sumType);
        } else {
            return depotItemMapper.buyOrSalePrice(type, subType, MId, MonthTime, sumType);
        }
    }

    public Double findGiftByType(String subType, Integer ProjectId, Long MId, String type) {
        if (IN.equals(type)) {
            return depotItemMapper.findGiftByTypeIn(subType, ProjectId, MId);
        } else {
            return depotItemMapper.findGiftByTypeOut(subType, ProjectId, MId);
        }
    }


}
