package com.vic.generator.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.vic.generator.properties.MyBatisPlusGeneratorProperties;

@Configuration
@EnableConfigurationProperties(MyBatisPlusGeneratorProperties.class)
public class GeneratorConfiguration {
    

}
