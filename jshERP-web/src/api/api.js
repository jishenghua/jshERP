import { getAction, deleteAction, putAction, postAction, httpAction } from '@/api/manage'

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
const roleAllList = (params)=>getAction("/role/allList",params);
//用户管理
const registerUser = (params)=>postAction("/user/registerUser",params);
const addUser = (params)=>postAction("/user/addUser",params);
const editUser = (params)=>putAction("/user/updateUser",params);
const getUserList = (params)=>getAction("/user/getUserList",params);
const queryPermissionsByUser = (params)=>postAction("/function/findMenuByPNumber",params);
//机构管理
const queryOrganizationTreeList = (params)=>getAction("/organization/getOrganizationTree",params);
const queryOrganizationById = (params)=>getAction("/organization/findById",params);
const checkOrganization = (params)=>getAction("/organization/checkIsNameExist",params);
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
const checkMaterialCategory = (params)=>getAction("/materialCategory/checkIsNameExist",params);
//商品管理
const addMaterial = (params)=>postAction("/material/add",params);
const editMaterial = (params)=>putAction("/material/update",params);
const checkMaterial = (params)=>getAction("/material/checkIsExist",params);
const getMaterialBySelect = (params)=>getAction("/material/findBySelect",params);
const getSerialMaterialBySelect = (params)=>getAction("/material/getMaterialEnableSerialNumberList",params);
const getMaterialByBarCode = (params)=>getAction("/material/getMaterialByBarCode",params);
const getMaxBarCode = (params)=>getAction("/material/getMaxBarCode",params);
const checkMaterialBarCode = (params)=>getAction("/materialsExtend/checkIsBarCodeExist",params);
const batchUpdateMaterial = (params)=>postAction("/material/batchUpdate",params);
//序列号
const addSerialNumber = (params)=>postAction("/serialNumber/add",params);
const editSerialNumber = (params)=>putAction("/serialNumber/update",params);
const checkSerialNumber = (params)=>getAction("/serialNumber/checkIsNameExist",params);
const batAddSerialNumber = (params)=>postAction("/serialNumber/batAddSerialNumber",params);
const getEnableSerialNumberList = (params)=>getAction("/serialNumber/getEnableSerialNumberList",params);
//多属性
const addMaterialAttribute = (params)=>postAction("/materialAttribute/add",params);
const editMaterialAttribute = (params)=>putAction("/materialAttribute/update",params);
const checkMaterialAttribute = (params)=>getAction("/materialAttribute/checkIsNameExist",params);
const getMaterialAttributeNameList = (params)=>getAction("/materialAttribute/getNameList",params);
const getMaterialAttributeValueListById = (params)=>getAction("/materialAttribute/getValueListById",params);
//功能管理
const addFunction = (params)=>postAction("/function/add",params);
const editFunction = (params)=>putAction("/function/update",params);
const checkFunction = (params)=>getAction("/function/checkIsNameExist",params);
const checkNumber = (params)=>getAction("/function/checkIsNumberExist",params);
//系统配置
const addSystemConfig = (params)=>postAction("/systemConfig/add",params);
const editSystemConfig = (params)=>putAction("/systemConfig/update",params);
const checkSystemConfig = (params)=>getAction("/systemConfig/checkIsNameExist",params);
const getCurrentSystemConfig = (params)=>getAction("/systemConfig/getCurrentInfo",params);
const fileSizeLimit = (params)=>getAction("/systemConfig/fileSizeLimit",params);
//平台参数
const addPlatformConfig = (params)=>postAction("/platformConfig/add",params);
const editPlatformConfig = (params)=>putAction("/platformConfig/update",params);
const getPlatformConfigByKey = (params)=>getAction("/platformConfig/getPlatformConfigByKey",params);
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
const checkSupplier = (params)=>getAction("/supplier/checkIsNameAndTypeExist",params);
const findBySelectSup = (params)=>postAction("/supplier/findBySelect_sup",params);
const findBySelectCus = (params)=>postAction("/supplier/findBySelect_cus",params);
const findBySelectRetail = (params)=>postAction("/supplier/findBySelect_retail",params);
const findBySelectOrgan = (params)=>postAction("/supplier/findBySelect_organ",params);
//单据相关
const findBillDetailByNumber = (params)=>getAction("/depotHead/getDetailByNumber",params);
const findStockByDepotAndBarCode = (params)=>getAction("/depotItem/findStockByDepotAndBarCode",params);
const getBatchNumberList = (params)=>getAction("/depotItem/getBatchNumberList",params);
const findFinancialDetailByNumber = (params)=>getAction("/accountHead/getDetailByNumber",params);

export {
  getBuyAndSaleStatistics,
  buyOrSalePrice,
  checkTenant,
  editTenant,
  addRole,
  editRole,
  checkRole,
  roleAllList,
  registerUser,
  addUser,
  editUser,
  getUserList,
  queryPermissionsByUser,
  queryOrganizationTreeList,
  queryOrganizationById,
  checkOrganization,
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
  checkMaterialCategory,
  addMaterial,
  editMaterial,
  checkMaterial,
  getMaterialBySelect,
  getSerialMaterialBySelect,
  getMaterialByBarCode,
  getMaxBarCode,
  checkMaterialBarCode,
  batchUpdateMaterial,
  addSerialNumber,
  editSerialNumber,
  checkSerialNumber,
  batAddSerialNumber,
  getEnableSerialNumberList,
  addMaterialAttribute,
  editMaterialAttribute,
  checkMaterialAttribute,
  getMaterialAttributeNameList,
  getMaterialAttributeValueListById,
  addFunction,
  editFunction,
  checkFunction,
  checkNumber,
  addSystemConfig,
  editSystemConfig,
  checkSystemConfig,
  getCurrentSystemConfig,
  fileSizeLimit,
  addPlatformConfig,
  editPlatformConfig,
  getPlatformConfigByKey,
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
  findBySelectOrgan,
  findBillDetailByNumber,
  findStockByDepotAndBarCode,
  getBatchNumberList,
  findFinancialDetailByNumber
}



