package com.jsh.erp.service.depotHead;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.DepotHeadMapper;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepotHeadService {
    private Logger logger = LoggerFactory.getLogger(DepotHeadService.class);

    @Resource
    private DepotHeadMapper depotHeadMapper;

    public DepotHead getDepotHead(long id) {
        return depotHeadMapper.selectByPrimaryKey(id);
    }

    public List<DepotHead> getDepotHead() {
        DepotHeadExample example = new DepotHeadExample();
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4List> select(String type, String subType, String number, String beginTime, String endTime, String dhIds, int offset, int rows) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapper.selectByConditionDepotHead(type, subType, number, beginTime, endTime, dhIds, offset, rows);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(Math.abs(dh.getChangeamount()));
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(Math.abs(dh.getTotalprice()));
                }
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }



    public int countDepotHead(String type, String subType, String number, String beginTime, String endTime, String dhIds) {
        return depotHeadMapper.countsByDepotHead(type, subType, number, beginTime, endTime, dhIds);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotHead(String beanJson, HttpServletRequest request) {
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        Object userInfo = request.getSession().getAttribute("user");
        if (userInfo != null) {
            User sessionUser = (User) userInfo;
            String uName = sessionUser.getUsername();
            depotHead.setOperpersonname(uName);
        }
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(false);
        return depotHeadMapper.insert(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotHead(String beanJson, Long id) {
        DepotHead dh = depotHeadMapper.selectByPrimaryKey(id);
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        depotHead.setId(id);
        depotHead.setStatus(dh.getStatus());
        depotHead.setCreatetime(dh.getCreatetime());
        depotHead.setOperpersonname(dh.getOperpersonname());
        return depotHeadMapper.updateByPrimaryKey(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotHead(Long id) {
        return depotHeadMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHead(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(idList);
        return depotHeadMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<DepotHead> list = depotHeadMapper.selectByExample(example);
        return list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String depotHeadIDs) {
        List<Long> ids = StringUtil.strToLongList(depotHeadIDs);
        DepotHead depotHead = new DepotHead();
        depotHead.setStatus(status);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(ids);
        return depotHeadMapper.updateByExampleSelective(depotHead, example);
    }

    public String buildNumber(String type, String subType, String beginTime, String endTime) {
        String newNumber = "0001"; //新编号
        try {
            DepotHeadExample example = new DepotHeadExample();
            example.createCriteria().andTypeEqualTo(type).andSubtypeEqualTo(subType)
                    .andOpertimeGreaterThanOrEqualTo(StringUtil.getDateByString(beginTime,null))
                    .andOpertimeLessThanOrEqualTo(StringUtil.getDateByString(endTime,null));
            example.setOrderByClause("Id desc");
            List<DepotHead> dataList = depotHeadMapper.selectByExample(example);
            //存放数据json数组
            if (null != dataList && dataList.size() > 0) {
                DepotHead depotHead = dataList.get(0);
                if (depotHead != null) {
                    String number = depotHead.getDefaultnumber(); //最大的单据编号
                    if (number != null) {
                        Integer lastNumber = Integer.parseInt(number.substring(12, 16)); //末四尾
                        lastNumber = lastNumber + 1;
                        Integer nLen = lastNumber.toString().length();
                        if (nLen == 1) {
                            newNumber = "000" + lastNumber.toString();
                        } else if (nLen == 2) {
                            newNumber = "00" + lastNumber.toString();
                        } else if (nLen == 3) {
                            newNumber = "0" + lastNumber.toString();
                        } else if (nLen == 4) {
                            newNumber = lastNumber.toString();
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>>>>>>>>>>>单据编号生成异常", e);
        }
        return newNumber;
    }

    public Long getMaxId() {
        return depotHeadMapper.getMaxId();
    }

    public String findMaterialsListByHeaderId(Long id) {
        String allReturn = depotHeadMapper.findMaterialsListByHeaderId(id);
        return allReturn;
    }

    public List<DepotHead> findByMonth(String monthTime) {
        DepotHeadExample example = new DepotHeadExample();
        monthTime = monthTime + "-31 00:00:00";
        Date month = StringUtil.getDateByString(monthTime, null);
        example.createCriteria().andOpertimeLessThanOrEqualTo(month);
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHead> getDepotHeadGiftOut(String projectId) {
        DepotHeadExample example = new DepotHeadExample();
        if (projectId != null) {
            example.createCriteria().andProjectidEqualTo(Long.parseLong(projectId));
        }
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4InDetail> findByAll(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapper.findByAll(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findByAllCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapper.findByAllCount(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4InOutMCount> findInOutMaterialCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapper.findInOutMaterialCount(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findInOutMaterialCountTotal(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapper.findInOutMaterialCountTotal(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4StatementAccount> findStatementAccount(String beginTime, String endTime, Integer organId, String supType, Integer offset, Integer rows) {
        return depotHeadMapper.findStatementAccount(beginTime, endTime, organId, supType, offset, rows);
    }

    public int findStatementAccountCount(String beginTime, String endTime, Integer organId, String supType) {
        return depotHeadMapper.findStatementAccountCount(beginTime, endTime, organId, supType);
    }

    public Double findAllMoney(Integer supplierId, String type, String subType, String mode, String endTime) {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "DiscountLastMoney";
        }
        return depotHeadMapper.findAllMoney(supplierId, type, subType, modeName, endTime);
    }

    public List<DepotHeadVo4List> getDetailByNumber(String number) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapper.getDetailByNumber(number);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(Math.abs(dh.getChangeamount()));
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(Math.abs(dh.getTotalprice()));
                }
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }

}
