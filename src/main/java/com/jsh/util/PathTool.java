package com.jsh.util;

import com.jsh.base.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 获取应用系统路径
 *
 * @author jishenghua
 * @qq 7 5 2 7 1 8 9 2 0
 */
public class PathTool {

    /**
     * 获取WEB-INF的绝对路径
     *
     * @return
     */
    public static String getWebinfPath() {
        String webinfPath = "";
        //获取URL对象
        URL url = PathTool.class.getClassLoader().getResource("");
        try {
            //获取路径
            webinfPath = url.toURI().getPath();
            //截取路径到WEB-INF结束
//            webinfPath = path.substring(0, path.indexOf("/WEB-INF") + 8);
        } catch (URISyntaxException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>路径获取异常", e);
        }
        return webinfPath;
    }

    /**
     * 获取webapp的绝对路径
     *
     * @return
     */
    public static String getWebappPath() {
        //先获取工程路径
        String projectPath = getProjectPath();
        //获取工程路径的上级路径
        File f = new File(projectPath);
        //路径不存在就返回
        if (!f.exists()) {
            return projectPath;
        } else {
            //返回webapp路径
            return f.getParent();
        }
    }

    /**
     * 获取工程的绝对路径
     *
     * @return
     */
    public static String getProjectPath() {
        String projectPath = "";
        //获取URL对象
        URL url = PathTool.class.getClassLoader().getResource("");
        String path = null;
        try {
            //获取路径
            path = url.toURI().getPath();
            //截取webapp路径
            projectPath = path.substring(0, path.indexOf("/WEB-INF"));
        } catch (URISyntaxException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>路径获取异常", e);
        }
        return projectPath;
    }
}
