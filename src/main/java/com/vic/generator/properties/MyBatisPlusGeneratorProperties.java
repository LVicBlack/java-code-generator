package com.vic.generator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.baomidou.mybatisplus.annotation.DbType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = MyBatisPlusGeneratorProperties.PREFIX)
public class MyBatisPlusGeneratorProperties {
    public static final String PREFIX = "mybatis-plus-generator";

    private String modulePath;

    private DataSourceConfig dataSourceConfig;
    private GlobalConfig globalConfig;
    private PackageConfig packageConfig;
    private StrategyConfig strategyConfig;

    public String getPath(){
        return modulePath + "/" + globalConfig.getBasePath();
    }

    public String getXmlPath(){
        return modulePath + "/" + packageConfig.getPackageMapperXml();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataSourceConfig {
        // 数据库类型
        @Default
        private String dbType = DbType.MYSQL.getDb();
        // 数据库连接地址
        private String dbUrl;
        // 数据库名称
        private String username;
        // 数据库密码
        private String password;
        // 数据库驱动
        private String driver;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GlobalConfig {

        // 作者
        @Default
        private String author = "vic";
        // 输入目录
        @Default
        private String basePath = "src/main/java";

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PackageConfig {
        // 包路径(package_path)
        private String packagePath;
        // 实体类包名
        @Default
        private String packageEntity = "entity";
        // mapper包名
        @Default
        private String packageMapper = "mapper";
        // mapper XML目录名
        @Default
        private String packageMapperXml = "src/main/resources/mapper";
        // service包名
        @Default
        private String packageService = "service";
        // serviceImpl包名
        @Default
        private String packageServiceImpl = "service.impl";
        // controller包名
        @Default
        private String packageController = "controller";
        
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StrategyConfig {
        // 表名
        private String[] tableNames;
        // 表前缀
        @Default
        private String[] tablePrefixes = {};
        // 字段前缀
        @Default
        private String[] fieldPrefixes = {};
        // 排出表的表名
        @Default
        private String[] excludeTableNames = {};
        // 忽略的字段
        @Default
        private String[] ignoreColumns = {};

        // 是否开启Lombok
        @Default
        private Boolean lombokModel = true;

        // 是否覆盖已生成文件
        @Default
        private Boolean fileOverride = true;

        private String fileNamePatternEntity;

        private String fieldLogicDelete;

        private String fieldVersion;

        private Boolean fieldAnnotation;

        private Boolean mapperAnnotation;

    }

}
