package com.jsh.erp.controller;

import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.module.PluginInfo;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ComputerInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.*;

/**
 * 插件jar 包测试功能
 * @author jishenghua
 * @version 1.0
 */
@RestController
@RequestMapping("/plugin")
@Api(tags = {"插件管理"})
public class PluginController {
    private Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Resource
    private UserService userService;

    private final PluginOperator pluginOperator;

    @Autowired
    public PluginController(PluginApplication pluginApplication) {
        this.pluginOperator = pluginApplication.getPluginOperator();
    }
    /**
     * 获取插件信息
     * @return 返回插件信息
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取插件信息")
    public BaseResponseInfo getPluginInfo(@RequestParam(value = "name",required = false) String name,
                                          @RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize,
                                          HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<PluginInfo> resList = new ArrayList<>();
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                List<PluginInfo> list = pluginOperator.getPluginInfo();
                if (StringUtil.isEmpty(name)) {
                    resList = list;
                } else {
                    for (PluginInfo pi : list) {
                        String desc = pi.getPluginDescriptor().getPluginDescription();
                        if (desc.contains(name)) {
                            resList.add(pi);
                        }
                    }
                }
            }
            map.put("rows", resList);
            map.put("total", resList.size());
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 获取插件jar文件名
     * @return 获取插件文件名。只在生产环境显示
     */
    @GetMapping("/files")
    @ApiOperation(value = "获取插件jar文件名")
    public Set<String> getPluginFilePaths(){
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                return pluginOperator.getPluginFilePaths();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * 根据插件id停止插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/stop/{id}")
    @ApiOperation(value = "根据插件id停止插件")
    public BaseResponseInfo stop(@PathVariable("id") String id){
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "";
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.stop(id)) {
                    message = "plugin '" + id + "' stop success";
                } else {
                    message = "plugin '" + id + "' stop failure";
                }
            } else {
                message = "power is limit";
            }
            map.put("message", message);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            map.put("message", "plugin '" + id +"' stop failure. " + e.getMessage());
            res.code = 500;
            res.data = map;
        }
        return res;
    }

    /**
     * 根据插件id启动插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/start/{id}")
    @ApiOperation(value = "根据插件id启动插件")
    public BaseResponseInfo start(@PathVariable("id") String id){
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "";
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.start(id)) {
                    message = "plugin '" + id + "' start success";
                } else {
                    message = "plugin '" + id + "' start failure";
                }
            } else {
                message = "power is limit";
            }
            map.put("message", message);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            map.put("message", "plugin '" + id +"' start failure. " + e.getMessage());
            res.code = 500;
            res.data = map;
        }
        return res;
    }


    /**
     * 根据插件id卸载插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/uninstall/{id}")
    @ApiOperation(value = "根据插件id卸载插件")
    public BaseResponseInfo uninstall(@PathVariable("id") String id){
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "";
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.uninstall(id, true)) {
                    message = "plugin '" + id + "' uninstall success";
                } else {
                    message = "plugin '" + id + "' uninstall failure";
                }
            } else {
                message = "power is limit";
            }
            map.put("message", message);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            map.put("message", "plugin '" + id +"' uninstall failure. " + e.getMessage());
            res.code = 500;
            res.data = map;
        }
        return res;
    }


    /**
     * 根据插件路径安装插件。该插件jar必须在服务器上存在。注意: 该操作只适用于生产环境
     * @param path 插件路径名称
     * @return 操作结果
     */
    @PostMapping("/installByPath")
    @ApiOperation(value = "根据插件路径安装插件")
    public String install(@RequestParam("path") String path){
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.install(Paths.get(path))) {
                    return "installByPath success";
                } else {
                    return "installByPath failure";
                }
            } else {
                return "installByPath failure";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "installByPath failure : " + e.getMessage();
        }
    }


    /**
     * 上传并安装插件。注意: 该操作只适用于生产环境
     * @param file 上传文件 multipartFile
     * @return 操作结果
     */
    @PostMapping("/uploadInstallPluginJar")
    @ApiOperation(value = "上传并安装插件")
    public BaseResponseInfo install(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                pluginOperator.uploadPluginAndStart(file);
                res.code = 200;
                res.data = "导入成功";
            } else {
                res.code = 500;
                res.data = "抱歉，无操作权限！";
            }
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "导入失败";
        }
        return res;
    }

    /**
     * 上传插件的配置文件。注意: 该操作只适用于生产环境
     * @param multipartFile 上传文件 multipartFile
     * @return 操作结果
     */
    @PostMapping("/uploadPluginConfigFile")
    @ApiOperation(value = "上传插件的配置文件")
    public String uploadConfig(@RequestParam("configFile") MultipartFile multipartFile){
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.uploadConfigFile(multipartFile)) {
                    return "uploadConfig success";
                } else {
                    return "uploadConfig failure";
                }
            } else {
                return "installByPath failure";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "uploadConfig failure : " + e.getMessage();
        }
    }


    /**
     * 备份插件。注意: 该操作只适用于生产环境
     * @param pluginId 插件id
     * @return 操作结果
     */
    @PostMapping("/back/{pluginId}")
    @ApiOperation(value = "备份插件")
    public String backupPlugin(@PathVariable("pluginId") String pluginId){
        try {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEFAULT_MANAGER.equals(userInfo.getLoginName())) {
                if (pluginOperator.backupPlugin(pluginId, "testBack")) {
                    return "backupPlugin success";
                } else {
                    return "backupPlugin failure";
                }
            } else {
                return "backupPlugin failure";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "backupPlugin failure : " + e.getMessage();
        }
    }

    /**
     * 获取加密后的mac
     * @return
     */
    @GetMapping("/getMacWithSecret")
    @ApiOperation(value = "获取加密后的mac")
    public BaseResponseInfo getMacWithSecret(){
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String mac = ComputerInfo.getMacAddress();
            res.code = 200;
            res.data = DigestUtils.md5DigestAsHex(mac.getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据插件标识判断是否存在
     * @param pluginIds 多个用逗号隔开
     * @return
     */
    @GetMapping("/checkByPluginId")
    @ApiOperation(value = "根据插件标识判断是否存在")
    public BaseResponseInfo checkByTag(@RequestParam("pluginIds") String pluginIds){
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            boolean data = false;
            if(StringUtil.isNotEmpty(pluginIds)) {
                String[] pluginIdList = pluginIds.split(",");
                List<PluginInfo> list = pluginOperator.getPluginInfo();
                for (PluginInfo pi : list) {
                    String info = pi.getPluginDescriptor().getPluginId();
                    for (int i = 0; i < pluginIdList.length; i++) {
                        if (pluginIdList[i].equals(info)) {
                            data = true;
                        }
                    }
                }
            }
            res.code = 200;
            res.data = data;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
