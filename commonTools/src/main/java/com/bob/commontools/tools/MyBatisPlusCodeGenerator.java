package com.bob.commontools.tools;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MyBatisPlusCodeGenerator {

    public static void main(String[] args) {
        // 数据库
        String schemaName = scanner("数据库 Schema Name");

        // 当前生成java代码路径
        String currentPath = System.getProperty("user.dir") + "/" + scanner("当前maven模块名") + "/src/main";

        // 当前生成mapper.xml代码路径
        String xmlPath = currentPath + "/resources/mapper/";

        // java代码模块名（Controller外层包的名字）
        String modelName = scanner("当前模块");
        FastAutoGenerator.create(
                        // 建立数据库连接
                        new DataSourceConfig.Builder(
                                "jdbc:mysql://192.168.50.240:3306/" + schemaName,
                                "root",
                                "javaee12"))

                // 全局配置
                .globalConfig(builder -> builder
                        .author("Bob")
                        // knife4j支持
                        .enableSpringdoc()
                        .outputDir(currentPath + "/java")

                )
                // 包配置
                .packageConfig(builder -> builder
                        .moduleName(modelName)
                        .parent("com.bob")
                        .controller("controller")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .entity("domain")
                        // 单独设置mapper.xml的生成路径
                        .xml("mapper").pathInfo(Collections.singletonMap(OutputFile.xml, xmlPath + modelName))
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        // 表
                        .addInclude(getTables(scanner("请输入表名，多个英文逗号分隔？所有输入 all")))

                        // 实体策略
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .logicDeleteColumnName("del_flag")
                        .versionColumnName("revision")
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)

                        // Controller策略
                        .controllerBuilder()
                        // 生成@RestController注解
                        .enableRestStyle()

                        // Service策略
                        .serviceBuilder()
                        // Service java类命名更改
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImp")

                        // Mapper策略
                        .mapperBuilder()
                        .enableBaseColumnList()
                        .enableBaseResultMap()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .build()

                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                // .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * @return : java.util.List<java.lang.String>
     * @description : 处理 all 情况
     * @params : [tables]
     * @date : 2024/11/1 15:56
     **/
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    /**
     * @return : java.lang.String
     * @description : 读取控制台内容
     * @params : [tip]
     * @date : 2024/11/1 15:57
     **/
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!ipt.isBlank()) {
                return ipt;
            }
        }
        throw new RuntimeException("请输入正确的" + tip + "！");
    }


}
