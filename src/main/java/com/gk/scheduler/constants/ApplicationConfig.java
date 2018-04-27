package com.gk.scheduler.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ApplicationConfig {

    public static String ENV;

    /**
     * 定时器实例名
     */
    public static String SCHEDULER_INSTANCE_NAME;

    /**
     * 定时器集群名
     */
    public static String SCHEDULER_CLUSTER_NAME;

    @Autowired
    private Environment env;

    /**
     * 初始化系统参数
     */
    public void init() {
        SCHEDULER_INSTANCE_NAME = env.getProperty("org.quartz.scheduler.instanceName");
        SCHEDULER_CLUSTER_NAME = env.getProperty("org.quartz.scheduler.clusterName");
        ENV =  env.getProperty("spring.profiles.active");
    }

}