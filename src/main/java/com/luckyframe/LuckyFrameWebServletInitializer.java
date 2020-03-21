package com.luckyframe;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 容器部署
 * @author Seagull
 * @date 2019年2月12日
 */
public class LuckyFrameWebServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(LuckyFrameWebApplication.class);
    }
}
