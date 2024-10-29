package com.vic.generator.generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;

@SpringBootTest
public class MyAutoGeneratorTest {
        @Resource
        private MyBatisPlusAutoGenerator autoGenerator;

        @Test
        void testExecute() {

                autoGenerator.execute();

        }
}
