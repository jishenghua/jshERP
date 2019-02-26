package com.jsh.erp.service.materialCategory;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.MaterialCategoryExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapper;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapperEx;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class MaterialCategoryService {
    private Logger logger = LoggerFactory.getLogger(MaterialCategoryService.class);

    @Resource
    private MaterialCategoryMapper materialCategoryMapper;
    @Resource
    private MaterialCategoryMapperEx materialCategoryMapperEx;
    @Resource
    private UserService userService;

    public MaterialCategory getMaterialCategory(long id) {
        return materialCategoryMapper.selectByPrimaryKey(id);
    }

    public List<MaterialCategory> getMaterialCategory() {
        MaterialCategoryExample example = new MaterialCategoryExample();
        return materialCategoryMapper.selectByExample(example);
    }

    public List<MaterialCategory> getAllList(Long parentId) {
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andParentidEqualTo(parentId).andIdNotEqualTo(1l);
        example.setOrderByClause("id");
        return materialCategoryMapper.selectByExample(example);
    }

    public List<MaterialCategory> select(String name, Integer parentId, int offset, int rows) {
        return materialCategoryMapperEx.selectByConditionMaterialCategory(name, parentId, offset, rows);
    }

    public Long countMaterialCategory(String name, Integer parentId) {
        return materialCategoryMapperEx.countsByMaterialCategory(name, parentId);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterialCategory(String beanJson, HttpServletRequest request) {
        MaterialCategory materialCategory = JSONObject.parseObject(beanJson, MaterialCategory.class);
        return materialCategoryMapper.insertSelective(materialCategory);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterialCategory(String beanJson, Long id) {
        MaterialCategory materialCategory = JSONObject.parseObject(beanJson, MaterialCategory.class);
        materialCategory.setId(id);
        return materialCategoryMapper.updateByPrimaryKeySelective(materialCategory);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterialCategory(Long id) {
        return materialCategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialCategory(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andIdIn(idList);
        return materialCategoryMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        return 0;
    }

    public List<MaterialCategory> findById(Long id) {
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andIdEqualTo(id);
        return materialCategoryMapper.selectByExample(example);
    }
    /**
     * create by: cjl
     * description:
     *获取商品类别树数据
     * create time: 2019/2/19 14:30
     * @Param:
     * @return java.util.List<com.jsh.erp.datasource.vo.TreeNode>
     */
    public List<TreeNode> getMaterialCategoryTree() throws Exception{
       return materialCategoryMapperEx.getNodeTree();
    }
    /**
     * create by: cjl
     * description:
     *  新增商品类别信息
     * create time: 2019/2/19 16:30
     * @Param: mc
     * @return void
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int addMaterialCategory(MaterialCategory mc) throws Exception {
        if(mc==null){
            return 0;
        }
        if(mc.getParentid()==null){
            //没有给定父级目录的id，默认设置父级目录为根目录
            mc.setParentid(BusinessConstants.MATERIAL_CATEGORY_ROOT_ID);
        }
        //检查商品类型编号是否已存在
        checkMaterialCategorySerialNo(mc);
        //数据状态新增时默认设置为启用
        mc.setStatus(BusinessConstants.MATERIAL_CATEGORY_STATUS_ENABLE);
        Date date=new Date();
        User userInfo=userService.getCurrentUser();
        //创建时间
        mc.setCreateTime(date);
        //创建人
        mc.setCreator(userInfo==null?null:userInfo.getId());
        //更新时间
        mc.setUpdateTime(date);
        //更新人
        mc.setUpdater(userInfo==null?null:userInfo.getId());
        return materialCategoryMapperEx.addMaterialCategory(mc);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialCategoryByIds(String ids) throws Exception {
        //更新时间
        Date updateDate =new Date();
        //更新人
        User userInfo=userService.getCurrentUser();
        Long updater=userInfo==null?null:userInfo.getId();
        StringBuffer sb=new StringBuffer();
        String strArray[]=ids.split(",");
        if(strArray.length<1){
            return 0;
        }
       return materialCategoryMapperEx.batchDeleteMaterialCategoryByIds(updateDate,updater,strArray);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int editMaterialCategory(MaterialCategory mc) {
        //检查商品类型编号是否已存在
        checkMaterialCategorySerialNo(mc);
        //更新时间
        mc.setUpdateTime(new Date());
        //更新人
        User userInfo=userService.getCurrentUser();
        mc.setUpdater(userInfo==null?null:userInfo.getId());
        return materialCategoryMapperEx.editMaterialCategory(mc);
    }
    /**
     * 根据商品类别编号判断商品类别是否已存在
     * */
    public void  checkMaterialCategorySerialNo(MaterialCategory mc) {
        if(mc==null){
            return;
        }
        if(StringUtil.isEmpty(mc.getSerialNo())){
            return;
        }
        //根据商品类别编号查询商品类别
        List<MaterialCategory> mList=materialCategoryMapperEx.getMaterialCategoryBySerialNo(mc.getSerialNo());
        if(mList==null||mList.size()<1){
            //未查询到对应数据，编号可用
            return;
        }
        if(mList.size()>1){
            //查询到的数据条数大于1，编号已存在
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG);
        }
        if(mc.getId()==null){
            //新增时，编号已存在
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG);
        }
        if(mc.getId()!=mList.get(0).getId()){
            //修改时，相同编号，id不同
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG);
        }
    }

}
