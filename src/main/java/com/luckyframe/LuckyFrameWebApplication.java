package com.luckyframe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动LuckyFrameWeb程序
 * @author Seagull
 * @date 2019年2月12日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.luckyframe.project.*.*.mapper")
public class LuckyFrameWebApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(LuckyFrameWebApplication.class, args);
        System.out.println("LuckyFrameWeb启动成功......");
    }
}