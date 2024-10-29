package com.vic.generator.generator;

import java.sql.Types;
import java.util.Collections;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.vic.generator.properties.MyBatisPlusGeneratorProperties;

import cn.hutool.core.util.BooleanUtil;
import jakarta.annotation.Resource;

/**
 * 代码生成器
 */
@Component
public class MyBatisPlusAutoGenerator {

    @Resource
    MyBatisPlusGeneratorProperties config;

    public void execute() {
        FastAutoGenerator.create(dataSourceBuilder(config.getDataSourceConfig()))
                .dataSourceConfig(dataSourceConfigBuilder(config))
                .globalConfig(globalConfigBuilder(config))
                .packageConfig(packageConfigBuilder(config))
                .strategyConfig(strategyConfigBuilder(config.getStrategyConfig()))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * datasource 配置
     * 
     * @param config
     * @return
     */
    private Builder dataSourceBuilder(MyBatisPlusGeneratorProperties.DataSourceConfig config) {
        return new DataSourceConfig.Builder(config.getDbUrl(), config.getUsername(),
                config.getPassword());
    }

    /**
     * type handler 配置
     * 
     * @param config
     * @return
     */
    private Consumer<DataSourceConfig.Builder> dataSourceConfigBuilder(MyBatisPlusGeneratorProperties config) {
        return builder -> {
            builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                // 自定义类型转换
                if (typeCode == Types.TINYINT) {
                    return DbColumnType.INTEGER;
                }
                return typeRegistry.getColumnType(metaInfo);
            });
        };
    }

    /**
     * 全局配置
     * <ul>
     * <li>作者
     * <li>输出路径
     * <li>时间格式
     * 
     * @param config
     * @return
     */
    private Consumer<GlobalConfig.Builder> globalConfigBuilder(MyBatisPlusGeneratorProperties config) {
        MyBatisPlusGeneratorProperties.GlobalConfig globalConfig = config.getGlobalConfig();
        return (builder) -> {
            builder.author(globalConfig.getAuthor())
                    .outputDir(config.getPath())
                    .commentDate("yyyy-MM-dd");
        };
    }

    /**
     * 包配置
     * <ul>
     * <li>包路径(package_path)
     * <li>controller / service / mapper / 实体类 / xml 包路径
     * 
     * @param config
     * @return
     */
    private Consumer<PackageConfig.Builder> packageConfigBuilder(MyBatisPlusGeneratorProperties config) {
        com.vic.generator.properties.MyBatisPlusGeneratorProperties.PackageConfig packageConfig = config
                .getPackageConfig();
        return (builder) -> {
            builder
                    .parent(packageConfig.getPackagePath())
                    .controller(packageConfig.getPackageController())
                    .entity(packageConfig.getPackageEntity())
                    .mapper(packageConfig.getPackageMapper())
                    // .xml(config.getXmlPath())
                    .service(packageConfig.getPackageService())
                    .serviceImpl(packageConfig.getPackageServiceImpl())
                    .pathInfo(Collections.singletonMap(OutputFile.xml, config.getXmlPath()));
        };
    }

    /**
     * 策略配置
     * <ul>
     * <li>scan table
     * <li>Entity 配置
     * <li>Controller 配置
     * <li>Service 配置
     * <li>Mapper 配置
     * 
     * @param config
     * @return
     */
    private Consumer<StrategyConfig.Builder> strategyConfigBuilder(
            MyBatisPlusGeneratorProperties.StrategyConfig config) {
        return (builder) -> {
            builder.addInclude(config.getTableNames())
                    .addFieldPrefix(config.getFieldPrefixes())
                    .addTablePrefix(config.getTablePrefixes())
                    .addExclude(config.getExcludeTableNames());

            Entity.Builder entityBuilder = builder.entityBuilder()
                    .naming(NamingStrategy.underline_to_camel)
                    .columnNaming(NamingStrategy.underline_to_camel)
                    // .formatFileName(config.getFileNamePatternEntity())
                    .idType(IdType.ASSIGN_ID)
                    .logicDeleteColumnName(config.getFieldLogicDelete())
                    .versionColumnName(config.getFieldVersion())
                    // .superClass(config.getSuperClassName())
                    .addIgnoreColumns(config.getIgnoreColumns())
                    // 字段注解
                    .enableTableFieldAnnotation()
                    // .enableColumnConstant()
                    .enableFileOverride();
            if (BooleanUtil.isTrue(config.getLombokModel())) {
                entityBuilder.enableLombok();
            }
            if (BooleanUtil.isTrue(config.getFileOverride())) {
                entityBuilder.enableFileOverride();
            }

            builder.mapperBuilder()
                    .mapperXmlTemplate("/templates/mapper.xml")
                    // .formatMapperFileName(config.getFileNamePatternEntity())
                    // .formatXmlFileName(config.getFileNamePatternEntity())
                    .enableBaseResultMap()
                    .enableBaseColumnList()
                    .enableFileOverride();

            builder.serviceBuilder()
                    .enableFileOverride();
            // .formatServiceFileName(config.getFileNamePatternEntity())
            // .formatServiceImplFileName(config.getFileNamePatternEntity())

            builder.controllerBuilder()
                    .enableRestStyle()
                    .enableHyphenStyle()
                    .enableFileOverride();
        };
    }
}
