/*
package com.tuandai.bigdata.baseproject.util;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

public class MySpringBeanJobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance;
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();

        // 这里将Job的实例创建喝管理交给Spring，使用Spring的beanFactory去获取Job实例，
        // 获取不到的话就交由Spring的beanFactory自动创建一个,并根据名称自动注入和检查依赖关系
        // 这样的话Job中就可以实现自动注入和实现AOP编程
        try {
            jobInstance = beanFactory.getBean(jobClass);
        } catch (Exception e) {
            jobInstance = beanFactory.createBean(jobClass, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
        }
        return jobInstance;
    }
}*/
