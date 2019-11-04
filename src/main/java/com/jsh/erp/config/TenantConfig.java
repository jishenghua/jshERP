package com.jsh.erp.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TenantConfig {
    /**
     * create by: qiankunpingtai
     * create time: 2019/4/28 14:28
     * website：https://qiankunpingtai.cn
     * description:
     * 实现多租户和无租户模式数据可以兼容在一个数据库中
     * 多租户模式：根据tenant_id=租户id来筛选个人数据
     * 无租户模式：根据tenant_id=-1来筛选数据
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(HttpServletRequest request) {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                //从session中获取租户id
                Object tenantId = request.getSession().getAttribute("tenantId");
                if(tenantId!=null){
                    //多租户模式，租户id从当前用户获取
                    return new LongValue(Long.parseLong(tenantId.toString()));
                } else {
                    //无租户模式，租户id为-1
                    return new LongValue(Long.valueOf(-1));
                }
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表
                if ("tbl_sequence".equals(tableName) || "dual".equals(tableName)|| "jsh_tenant".equals(tableName)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                // 过滤自定义查询，此处跳过指定id的查询（不追加租户id过滤条件）
                if ("com.jsh.erp.datasource.mappers.UserMapperEx.getUserListByLoginName".equals(ms.getId())||
                        "com.jsh.erp.datasource.mappers.UserMapperEx.getUserListByloginNameAndPassword".equals(ms.getId())||
                        "com.jsh.erp.datasource.mappers.DepotItemMapperEx.getCurrentRepByMaterialIdAndDepotId".equals(ms.getId())) {
                    return true;
                }
                return false;

            }
        });
        return paginationInterceptor;
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.jsh.erp.datasource.mappers*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.jsh.erp.datasource.mappers*");
        return scannerConfigurer;
    }


    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }


}
