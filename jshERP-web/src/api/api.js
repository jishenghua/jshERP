import { getAction, deleteAction, putAction, postAction, httpAction } from '@/api/manage'
import Vue from 'vue'
import {UI_CACHE_DB_DICT_DATA } from "@/store/mutation-types"

//首页统计
const getBuyAndSaleStatistics = (params)=>getAction("/depotHead/getBuyAndSaleStatistics",params);
const buyOrSalePrice = (params)=>getAction("/depotItem/buyOrSalePrice",params);

//租户管理
const checkTenant = (params)=>getAction("/tenant/checkIsNameExist",params);
const editTenant = (params)=>putAction("/tenant/update",params);

//角色管理
const addRole = (params)=>postAction("/role/add",params);
const editRole = (params)=>putAction("/role/update",params);
const checkRole = (params)=>getAction("/role/checkIsNameExist",params);
const findUserRole = (params)=>getAction("/role/findUserRole",params);
const queryall = (params)=>getAction("/sys/role/queryall",params);

//用户管理
const registerUser = (params)=>postAction("/user/registerUser",params);
const addUser = (params)=>postAction("/user/addUser",params);
const editUser = (params)=>putAction("/user/updateUser",params);
const getUserList = (params)=>getAction("/user/getUserList",params);
//验证用户是否存在
const checkOnlyUser = (params)=>getAction("/sys/user/checkOnlyUser",params);
//改变密码
const changePassword = (params)=>putAction("/sys/user/changePassword",params);

//权限管理
const addPermission= (params)=>postAction("/sys/permission/add",params);
const editPermission= (params)=>putAction("/sys/permission/edit",params);
const getPermissionList = (params)=>getAction("/sys/permission/list",params);
const getSystemMenuList = (params)=>getAction("/sys/permission/getSystemMenuList",params);
const getSystemSubmenu = (params)=>getAction("/sys/permission/getSystemSubmenu",params);
const getSystemSubmenuBatch = (params) => getAction('/sys/permission/getSystemSubmenuBatch', params)

const queryTreeList = (params)=>getAction("/sys/permission/queryTreeList",params);
const queryTreeListForRole = (params)=>getAction("/sys/role/queryTreeList",params);
const queryListAsync = (params)=>getAction("/sys/permission/queryListAsync",params);
const queryRolePermission = (params)=>getAction("/sys/permission/queryRolePermission",params);
const saveRolePermission = (params)=>postAction("/sys/permission/saveRolePermission",params);
const queryPermissionsByUser = (params)=>postAction("/function/findMenuByPNumber",params);
const loadAllRoleIds = (params)=>getAction("/sys/permission/loadAllRoleIds",params);
const getPermissionRuleList = (params)=>getAction("/sys/permission/getPermRuleListByPermId",params);
const queryPermissionRule = (params)=>getAction("/sys/permission/queryPermissionRule",params);

//机构管理
const queryOrganizationTreeList = (params)=>getAction("/organization/getOrganizationTree",params);
const queryOrganizationById = (params)=>getAction("/organization/findById",params);
const queryIdTree = (params)=>getAction("/sys/sysDepart/queryIdTree",params);
const queryParentName   = (params)=>getAction("/sys/sysDepart/queryParentName",params);
const searchByKeywords   = (params)=>getAction("/sys/sysDepart/searchBy",params);
const deleteByDepartId   = (params)=>deleteAction("/sys/sysDepart/delete",params);

//二级部门管理
const queryDepartPermission = (params)=>getAction("/sys/permission/queryDepartPermission",params);
const saveDepartPermission = (params)=>postAction("/sys/permission/saveDepartPermission",params);
const queryTreeListForDeptRole = (params)=>getAction("/sys/sysDepartPermission/queryTreeListForDeptRole",params);
const queryDeptRolePermission = (params)=>getAction("/sys/sysDepartPermission/queryDeptRolePermission",params);
const saveDeptRolePermission = (params)=>postAction("/sys/sysDepartPermission/saveDeptRolePermission",params);
const queryMyDepartTreeList = (params)=>getAction("/sys/sysDepart/queryMyDeptTreeList",params);

//日志管理
const deleteLog = (params)=>deleteAction("/sys/log/delete",params);
const deleteLogList = (params)=>deleteAction("/sys/log/deleteBatch",params);

//数据字典
const addDict = (params)=>postAction("/sys/dict/add",params);
const editDict = (params)=>putAction("/sys/dict/edit",params);
const treeList = (params)=>getAction("/sys/dict/treeList",params);
const addDictItem = (params)=>postAction("/sys/dictItem/add",params);
const editDictItem = (params)=>putAction("/sys/dictItem/edit",params);

//字典标签专用（通过code获取字典数组）
export const ajaxGetDictItems = (code, params)=>getAction(`/systemConfig/getDictItems/${code}`,params);
//从缓存中获取字典配置
function getDictItemsFromCache(dictCode) {
  if (Vue.ls.get(UI_CACHE_DB_DICT_DATA) && Vue.ls.get(UI_CACHE_DB_DICT_DATA)[dictCode]) {
    let dictItems = Vue.ls.get(UI_CACHE_DB_DICT_DATA)[dictCode];
    console.log("-----------getDictItemsFromCache----------dictCode="+dictCode+"---- dictItems=",dictItems)
    return dictItems;
  }
}

//系统通告
const doReleaseData = (params)=>getAction("/sys/annountCement/doReleaseData",params);
const doReovkeData = (params)=>getAction("/sys/annountCement/doReovkeData",params);

// 根据部门主键查询用户信息
const queryUserByDepId = (params)=>getAction("/sys/user/queryUserByDepId",params);
// 重复校验
const duplicateCheck = (params)=>getAction("/sys/duplicate/check",params);
// 加载分类字典
const loadCategoryData = (params)=>getAction("/sys/category/loadAllData",params);
const checkRuleByCode = (params) => getAction('/sys/checkRule/checkByCode', params)
//我的通告
const getUserNoticeInfo= (params)=>getAction("/sys/sysAnnouncementSend/getMyAnnouncementSend",params);

//经手人管理
const addPerson = (params)=>postAction("/person/add",params);
const editPerson = (params)=>putAction("/person/update",params);
const checkPerson = (params)=>getAction("/person/checkIsNameExist",params);
const getPersonByType = (params)=>getAction("/person/getPersonByType",params);
const getPersonByNumType = (params)=>getAction("/person/getPersonByNumType",params);
//账户管理
const addAccount = (params)=>postAction("/account/add",params);
const editAccount = (params)=>putAction("/account/update",params);
const checkAccount = (params)=>getAction("/account/checkIsNameExist",params);
const getAccount = (params)=>getAction("/account/getAccount",params);
//收支项目
const addInOutItem = (params)=>postAction("/inOutItem/add",params);
const editInOutItem = (params)=>putAction("/inOutItem/update",params);
const checkInOutItem = (params)=>getAction("/inOutItem/checkIsNameExist",params);
const findInOutItemByParam = (params)=>getAction("/inOutItem/findBySelect",params);
//仓库信息
const addDepot = (params)=>postAction("/depot/add",params);
const editDepot = (params)=>putAction("/depot/update",params);
const checkDepot = (params)=>getAction("/depot/checkIsNameExist",params);
//商品属性
const editMaterialProperty = (params)=>putAction("/materialProperty/update",params);
//商品类型
const queryMaterialCategoryTreeList = (params)=>getAction("/materialCategory/getMaterialCategoryTree",params);
const queryMaterialCategoryById = (params)=>getAction("/materialCategory/findById",params);
//商品管理
const addMaterial = (params)=>postAction("/material/add",params);
const editMaterial = (params)=>putAction("/material/update",params);
const checkMaterial = (params)=>getAction("/material/checkIsExist",params);
const getMaterialBySelect = (params)=>getAction("/material/findBySelect",params);
const getSerialMaterialBySelect = (params)=>getAction("/material/getMaterialEnableSerialNumberList",params);
const getMaterialByBarCode = (params)=>getAction("/material/getMaterialByBarCode",params);
const checkMaterialBarCode = (params)=>getAction("/materialsExtend/checkIsBarCodeExist",params);
//序列号
const addSerialNumber = (params)=>postAction("/serialNumber/add",params);
const editSerialNumber = (params)=>putAction("/serialNumber/update",params);
const checkSerialNumber = (params)=>getAction("/serialNumber/checkIsNameExist",params);
const batAddSerialNumber = (params)=>postAction("/serialNumber/batAddSerialNumber",params);
//功能管理
const addFunction = (params)=>postAction("/function/add",params);
const editFunction = (params)=>putAction("/function/update",params);
const checkFunction = (params)=>getAction("/function/checkIsNameExist",params);
//系统配置
const addSystemConfig = (params)=>postAction("/systemConfig/add",params);
const editSystemConfig = (params)=>putAction("/systemConfig/update",params);
const checkSystemConfig = (params)=>getAction("/systemConfig/checkIsNameExist",params);
const getCurrentSystemConfig = (params)=>getAction("/systemConfig/getCurrentInfo",params);
//用户|角色|模块关系
const addUserBusiness = (params)=>postAction("/userBusiness/add",params);
const editUserBusiness = (params)=>putAction("/userBusiness/update",params);
const checkUserBusiness = (params)=>getAction("/userBusiness/checkIsValueExist",params);
const updateBtnStrByRoleId = (params)=>postAction("/userBusiness/updateBtnStr",params);
//计量单位
const addUnit = (params)=>postAction("/unit/add",params);
const editUnit = (params)=>putAction("/unit/update",params);
const checkUnit = (params)=>getAction("/unit/checkIsNameExist",params);
//供应商|客户|会员
const addSupplier = (params)=>postAction("/supplier/add",params);
const editSupplier = (params)=>putAction("/supplier/update",params);
const checkSupplier = (params)=>getAction("/supplier/checkIsNameExist",params);
const findBySelectSup = (params)=>postAction("/supplier/findBySelect_sup",params);
const findBySelectCus = (params)=>postAction("/supplier/findBySelect_cus",params);
const findBySelectRetail = (params)=>postAction("/supplier/findBySelect_retail",params);
const findSupplierById = (params)=>getAction("/supplier/findById",params);
//单据相关
const findDepotHeadTotalPay = (params)=>getAction("/depotHead/findTotalPay",params);
const findBillDetailByNumber = (params)=>getAction("/depotHead/getDetailByNumber",params);
const findStockByDepotAndBarCode = (params)=>getAction("/depotItem/findStockByDepotAndBarCode",params);
const findAccountHeadTotalPay = (params)=>getAction("/accountHead/findTotalPay",params);
const findFinancialDetailByNumber = (params)=>getAction("/accountHead/getDetailByNumber",params);

export {
  getBuyAndSaleStatistics,
  buyOrSalePrice,
  checkTenant,
  editTenant,
  addRole,
  editRole,
  checkRole,
  findUserRole,
  registerUser,
  addUser,
  editUser,
  getUserList,
  queryall,
  checkOnlyUser,
  changePassword,
  getPermissionList,
  addPermission,
  editPermission,
  queryTreeList,
  queryListAsync,
  queryRolePermission,
  saveRolePermission,
  queryPermissionsByUser,
  loadAllRoleIds,
  getPermissionRuleList,
  queryPermissionRule,
  queryOrganizationTreeList,
  queryOrganizationById,
  queryParentName,
  searchByKeywords,
  deleteByDepartId,
  deleteLog,
  deleteLogList,
  addDict,
  editDict,
  treeList,
  addDictItem,
  editDictItem,
  doReleaseData,
  doReovkeData,
  queryUserByDepId,
  duplicateCheck,
  queryTreeListForRole,
  getSystemMenuList,
  getSystemSubmenu,
  getSystemSubmenuBatch,
  loadCategoryData,
  checkRuleByCode,
  queryDepartPermission,
  saveDepartPermission,
  queryTreeListForDeptRole,
  queryDeptRolePermission,
  saveDeptRolePermission,
  queryMyDepartTreeList,
  getUserNoticeInfo,
  getDictItemsFromCache,
  addPerson,
  editPerson,
  checkPerson,
  getPersonByType,
  getPersonByNumType,
  addAccount,
  editAccount,
  checkAccount,
  getAccount,
  addInOutItem,
  editInOutItem,
  checkInOutItem,
  findInOutItemByParam,
  addDepot,
  editDepot,
  checkDepot,
  editMaterialProperty,
  queryMaterialCategoryTreeList,
  queryMaterialCategoryById,
  addMaterial,
  editMaterial,
  checkMaterial,
  getMaterialBySelect,
  getSerialMaterialBySelect,
  getMaterialByBarCode,
  checkMaterialBarCode,
  addSerialNumber,
  editSerialNumber,
  checkSerialNumber,
  batAddSerialNumber,
  addFunction,
  editFunction,
  checkFunction,
  addSystemConfig,
  editSystemConfig,
  checkSystemConfig,
  getCurrentSystemConfig,
  addUserBusiness,
  editUserBusiness,
  checkUserBusiness,
  updateBtnStrByRoleId,
  addUnit,
  editUnit,
  checkUnit,
  addSupplier,
  editSupplier,
  checkSupplier,
  findBySelectSup,
  findBySelectCus,
  findBySelectRetail,
  findSupplierById,
  findDepotHeadTotalPay,
  findBillDetailByNumber,
  findStockByDepotAndBarCode,
  findAccountHeadTotalPay,
  findFinancialDetailByNumber
}



