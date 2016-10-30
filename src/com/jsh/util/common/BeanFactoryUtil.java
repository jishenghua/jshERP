/**
 * 项   目  名：HiTV
 * 包          名：com.suma.hitv.utils
 * 文   件  名：BeanFactoryUtil.java
 * 版本信息：V1.0
 * 日          期：2011-12-30-下午01:25:00
 * Copyright (c) 2000-2011北京数码视讯有限公司版权所有
 * 
 */
package com.jsh.util.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 获取spring配置中的bean对象,是单例，只会加载一次，请注意使用
 * 注意：此工具类默认处理UI组件WEB-INF目录下的applicationContext.xml配置文件,请注意文件 名和路径
 * @author  andy
 * @version V1.0
 */
public class BeanFactoryUtil
{
    private static BeanFactoryUtil defaultBeanFactory;
    
    private ApplicationContext defaultAC = null;
    
    //private ApplicationContext autoLoadAC = null;
    
    private static BeanFactoryUtil specialBeanFactory;
    
    private ApplicationContext specialAC = null;
    
    private static Map<String, ApplicationContext> beanMap = new HashMap<String, ApplicationContext>();
    
    //private Logger log = Logger.getLogger(BeanFactoryUtil.class);
    
    /**
     * 私有构造函数,默认为UI组件WEB-INF目录下的applicationContext.xml配置文件
     */
    private BeanFactoryUtil()
    {
        String fileUrl = PathTool.getWebinfPath();
        //这里只对UI组件WEB-INF目录下的applicationContext.xml配置文件
        defaultAC = new FileSystemXmlApplicationContext( new
        		 String[]{fileUrl
                         + "spring/basic-applicationContext.xml",
                         fileUrl + "spring/dao-applicationContext.xml"});
    }
    
    /**
     * 私有构造函数,带有文件的classpath路径,可能是非applicationContext.xml文件
     */
    private BeanFactoryUtil(String fileClassPath)
    {
        specialAC = new ClassPathXmlApplicationContext("classpath:"
                + fileClassPath);
    }
    
    /**
     * 非web.xml方式加载spring配置文件方式的实体实例获取方式
     * @param fileClassPath
     * @param beanName
     * @return 
     */
    public synchronized static Object getBeanByClassPathAndBeanName(
            String fileClassPath, String beanName)
    {
        ApplicationContext ac = beanMap.get(fileClassPath);
        if (null == ac)
        {
            ac = new ClassPathXmlApplicationContext("classpath:"
                    + fileClassPath);
            beanMap.put(fileClassPath, ac);
        }
        return ac.getBean(beanName);
    }
    
    /**
     * 获取类实例
     * 默认加载UI组件WEB-INF目录下的applicationContext.xml配置文件
     * @return 
     *
     */
    public synchronized static BeanFactoryUtil getInstance()
    {
        if (null == defaultBeanFactory)
        {
            defaultBeanFactory = new BeanFactoryUtil();
        }
        return defaultBeanFactory;
    }
    
    /**
     * 获取类实例,这种情况一定是在依赖其他组件时没有在applicationContext.xml加载器spring文件时使用
     * 这种情况请少用
     * @param fileClassPath
     * @return 
     */
    @Deprecated
    public synchronized static BeanFactoryUtil getInstance(String fileClassPath)
    {
        if (null == specialBeanFactory)
        {
            specialBeanFactory = new BeanFactoryUtil(fileClassPath);
        }
        return specialBeanFactory;
    }
    
    /**
     * 获取UI组件WEB-INF目录下的applicationContext.xml配置文件中配置的bean实例
     * @param beanName
     * @return 
     */
    public Object getBean(String beanName)
    {
        return defaultAC.getBean(beanName);
    }
    
    /**
     * 获取没有在applicationContext.xml配置文件中引入的spring配置文件，即没有用容器加载过的配置文件
     * 这里为特殊情况下使用,不推荐使用
     * 推荐在applicationContext.xml配置文件中引入需要使用的spring配置文件,然后使用BeanFactoryUtil.getInstance().getBean("")方法
     * @param beanName
     * @return 
     */
    @Deprecated
    public Object getSpecialBean(String beanName)
    {
        return specialAC.getBean(beanName);
    }
}
