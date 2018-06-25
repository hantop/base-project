/*
package com.tuandai.bigdata.baseproject.util;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {
    private static final String QUARTZ_CONFIG = "quartz.properties";

    @Bean
    public MySpringBeanJobFactory mySpringBeanJobFactory(){
        return new MySpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // 配置使用自定义的JobFactory
        schedulerFactoryBean.setJobFactory(mySpringBeanJobFactory());
        schedulerFactoryBean.setAutoStartup(true);
        // 设置quartz配置文件路径
        schedulerFactoryBean.setConfigLocation(new ClassPathResource(QUARTZ_CONFIG));
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
*/
