package com.jsh.erp.service.materialCategory;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.MaterialCategoryExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapper;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapperEx;
import com.jsh.erp.datasource.mappers.MaterialMapperEx;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
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
import java.util.ArrayList;
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
    @Resource
    private LogService logService;
    @Resource
    private MaterialMapperEx materialMapperEx;

    public MaterialCategory getMaterialCategory(long id)throws Exception {
        MaterialCategory result=null;
        try{
            result=materialCategoryMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<MaterialCategory> getMaterialCategoryListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<MaterialCategory> list = new ArrayList<>();
        try{
            MaterialCategoryExample example = new MaterialCategoryExample();
            example.createCriteria().andIdIn(idList);
            list = materialCategoryMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialCategory> getMaterialCategory()throws Exception {
        MaterialCategoryExample example = new MaterialCategoryExample();
        List<MaterialCategory> list=null;
        try{
            list=materialCategoryMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialCategory> getAllList(Long parentId)throws Exception {
        List<MaterialCategory> list=null;
        try{
            list = getMCList(parentId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialCategory> getMCList(Long parentId)throws Exception {
        List<MaterialCategory> res= new ArrayList<MaterialCategory>();
        List<MaterialCategory> list=null;
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId).andIdNotEqualTo(1L);
        example.setOrderByClause("id");
        list=materialCategoryMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            res.addAll(list);
            for(MaterialCategory mc : list) {
                List<MaterialCategory> mcList = getMCList(mc.getId());
                if(mcList!=null && mcList.size()>0) {
                    res.addAll(mcList);
                }
            }
        }
        return res;
    }

    public List<MaterialCategory> select(String name, Integer parentId) throws Exception{
        List<MaterialCategory> list=null;
        try{
            PageUtils.startPage();
            list=materialCategoryMapperEx.selectByConditionMaterialCategory(name, parentId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterialCategory(JSONObject obj, HttpServletRequest request)throws Exception {
        MaterialCategory materialCategory = JSONObject.parseObject(obj.toJSONString(), MaterialCategory.class);
        materialCategory.setCreateTime(new Date());
        materialCategory.setUpdateTime(new Date());
        int result=0;
        try{
            result=materialCategoryMapper.insertSelective(materialCategory);
            logService.insertLog("商品类型",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(materialCategory.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterialCategory(JSONObject obj, HttpServletRequest request) throws Exception{
        MaterialCategory materialCategory = JSONObject.parseObject(obj.toJSONString(), MaterialCategory.class);
        materialCategory.setUpdateTime(new Date());
        int result=0;
        try{
            result=materialCategoryMapperEx.editMaterialCategory(materialCategory);
            logService.insertLog("商品类型",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(materialCategory.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterialCategory(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialCategoryByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialCategory(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialCategoryByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialCategoryByIds(String ids) throws Exception {
        int result=0;
        String [] idArray=ids.split(",");
        //校验产品表	jsh_material
        List<Material> materialList=null;
        try{
            materialList= materialMapperEx.getMaterialListByCategoryIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(materialList!=null&&materialList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,CategoryIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<MaterialCategory> list = getMaterialCategoryListByIds(ids);
        for(MaterialCategory materialCategory: list){
            sb.append("[").append(materialCategory.getName()).append("]");
        }
        logService.insertLog("商品类型", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //更新时间
        Date updateDate =new Date();
        //更新人
        User userInfo=userService.getCurrentUser();
        Long updater=userInfo==null?null:userInfo.getId();
        String strArray[]=ids.split(",");
        if(strArray.length<1){
            return 0;
        }
        List<MaterialCategory> mcList = materialCategoryMapperEx.getMaterialCategoryListByCategoryIds(idArray);
        if(mcList!=null && mcList.size()>0) {
            logger.error("异常码[{}],异常提示[{}]",
                    ExceptionConstants.MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_CODE,ExceptionConstants.MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_MSG);
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_MSG);
        } else {
            result=materialCategoryMapperEx.batchDeleteMaterialCategoryByIds(updateDate,updater,strArray);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialCategory> list=null;
        try{
            list= materialCategoryMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public List<MaterialCategory> findById(Long id)throws Exception {
        List<MaterialCategory> list=null;
        if(id!=null) {
            MaterialCategoryExample example = new MaterialCategoryExample();
            example.createCriteria().andIdEqualTo(id);
            try{
                list=materialCategoryMapper.selectByExample(example);
            }catch(Exception e){
                JshException.readFail(logger, e);
            }
        }
        return list;
    }
    /**
     * description:
     * 获取商品类别树数据
     */
    public List<TreeNode> getMaterialCategoryTree(Long id) throws Exception{
        List<TreeNode> list=null;
        try{
            list=materialCategoryMapperEx.getNodeTree(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
       return list;
    }
    /**
     * 根据商品类别编号判断商品类别是否已存在
     * */
    public void  checkMaterialCategorySerialNo(MaterialCategory mc)throws Exception {
        if(mc==null){
            return;
        }
        if(StringUtil.isEmpty(mc.getSerialNo())){
            return;
        }
        //根据商品类别编号查询商品类别
        List<MaterialCategory> mList=null;
        try{
            mList= materialCategoryMapperEx.getMaterialCategoryBySerialNo(mc.getSerialNo(), mc.getId());
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
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
        /**
         * 包装类型用equals来比较
         * */
        if(mc.getId().equals(mList.get(0).getId())){
            //修改时，相同编号，id不同
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE,
                    ExceptionConstants.MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG);
        }
    }

    /**
     * 根据名称获取类型
     * @param name
     */
    public Long getCategoryIdByName(String name){
        Long categoryId = null;
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialCategory> list = materialCategoryMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            categoryId = list.get(0).getId();
        }
        return categoryId;
    }
}
