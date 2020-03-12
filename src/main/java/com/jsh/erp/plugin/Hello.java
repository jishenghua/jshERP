package com.jsh.erp.plugin;

import org.pf4j.ExtensionPoint;

/**
 * 测试非Spring管理的bean接口
 *  实现类需要使用 @Extension 注解
 *
 * @author jishenghua
 * @version 1.0
 */
public interface Hello extends ExtensionPoint {

    String getName();

}
