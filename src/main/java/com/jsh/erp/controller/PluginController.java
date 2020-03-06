package com.jsh.erp.controller;

import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.module.PluginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * 插件jar 包测试功能
 * @author jishenghua
 * @version 1.0
 */
@RestController
@RequestMapping("/plugin")
public class PluginController {


    private final PluginOperator pluginOperator;

    @Autowired
    public PluginController(PluginApplication pluginApplication) {
        this.pluginOperator = pluginApplication.getPluginOperator();
    }
    /**
     * 获取插件信息
     * @return 返回插件信息
     */
    @GetMapping
    public List<PluginInfo> getPluginInfo(){
        return pluginOperator.getPluginInfo();
    }

    /**
     * 获取插件jar文件名
     * @return 获取插件文件名。只在生产环境显示
     */
    @GetMapping("/files")
    public Set<String> getPluginFilePaths(){
        try {
            return pluginOperator.getPluginFilePaths();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据插件id停止插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/stop/{id}")
    public String stop(@PathVariable("id") String id){
        try {
            if(pluginOperator.stop(id)){
                return "plugin '" + id +"' stop success";
            } else {
                return "plugin '" + id +"' stop failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "plugin '" + id +"' stop failure. " + e.getMessage();
        }
    }

    /**
     * 根据插件id启动插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/start/{id}")
    public String start(@PathVariable("id") String id){
        try {
            if(pluginOperator.start(id)){
                return "plugin '" + id +"' start success";
            } else {
                return "plugin '" + id +"' start failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "plugin '" + id +"' start failure. " + e.getMessage();
        }
    }


    /**
     * 根据插件id卸载插件
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/uninstall/{id}")
    public String uninstall(@PathVariable("id") String id){
        try {
            if(pluginOperator.uninstall(id, true)){
                return "plugin '" + id +"' uninstall success";
            } else {
                return "plugin '" + id +"' uninstall failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "plugin '" + id +"' uninstall failure. " + e.getMessage();
        }
    }


    /**
     * 根据插件路径安装插件。该插件jar必须在服务器上存在。注意: 该操作只适用于生产环境
     * @param path 插件路径名称
     * @return 操作结果
     */
    @PostMapping("/installByPath")
    public String install(@RequestParam("path") String path){
        try {
            if(pluginOperator.install(Paths.get(path))){
                return "installByPath success";
            } else {
                return "installByPath failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "installByPath failure : " + e.getMessage();
        }
    }


    /**
     * 上传并安装插件。注意: 该操作只适用于生产环境
     * @param multipartFile 上传文件 multipartFile
     * @return 操作结果
     */
    @PostMapping("/uploadInstallPluginJar")
    public String install(@RequestParam("jarFile") MultipartFile multipartFile){
        try {
            if(pluginOperator.uploadPluginAndStart(multipartFile)){
                return "install success";
            } else {
                return "install failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "install failure : " + e.getMessage();
        }
    }


    /**
     * 上传插件的配置文件。注意: 该操作只适用于生产环境
     * @param multipartFile 上传文件 multipartFile
     * @return 操作结果
     */
    @PostMapping("/uploadPluginConfigFile")
    public String uploadConfig(@RequestParam("configFile") MultipartFile multipartFile){
        try {
            if(pluginOperator.uploadConfigFile(multipartFile)){
                return "uploadConfig success";
            } else {
                return "uploadConfig failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "uploadConfig failure : " + e.getMessage();
        }
    }


    /**
     * 备份插件。注意: 该操作只适用于生产环境
     * @param pluginId 插件id
     * @return 操作结果
     */
    @PostMapping("/back/{pluginId}")
    public String backupPlugin(@PathVariable("pluginId") String pluginId){
        try {
            if(pluginOperator.backupPlugin(pluginId, "testBack")){
                return "backupPlugin success";
            } else {
                return "backupPlugin failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "backupPlugin failure : " + e.getMessage();
        }
    }

}
