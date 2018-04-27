package com.gk.scheduler.entity;

/**
 * 定时器触发器
 * Quartz自带数据库表SCHE_TRIGGERS
 */
public class ScheduleTrigger {

    /**
     * 触发器名 - 临时触发器前缀
     */
    public static final String TRIGGER_NAME_MT_PREFIX = "MT_";

    /**
     * 集群名
     */
    private String schedName;
    /**
     * 触发器名
     */
    private String triggerName;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 触发器状态
     */
    private String triggerState;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }
}