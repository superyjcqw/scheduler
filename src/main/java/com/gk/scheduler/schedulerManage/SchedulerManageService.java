package com.gk.scheduler.schedulerManage;

import com.gk.scheduler.quartz.common.InvokingProxyJob;
import com.gk.scheduler.util.BeanManager;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class SchedulerManageService {

    private static final String JOB_GROUP = "DEFAULT";
    private static final String TRIGGER_GROUP = "DEFAULT";

    private Scheduler scheduler;

    public void init() {
        scheduler = BeanManager.getBean(SchedulerFactoryBean.class).getScheduler();
    }

    /**
     * 添加定时器
     * @throws SchedulerException
     */
    public void add(String jobName, String jobDesc, String triggerType, String triggerExpression) throws SchedulerException {

        String triggerName = jobName + "Trigger";
        JobDetail jobDetail = JobBuilder.newJob(InvokingProxyJob.class)
                .withIdentity(jobName, JOB_GROUP)
                .withDescription(jobDesc)
//                .usingJobData("_id", 1)
                .build();
        scheduler.scheduleJob(jobDetail, buildTrigger(triggerType, triggerName, TRIGGER_GROUP, triggerExpression, jobDesc));
    }

    /**
     * 修改定时器
     * @throws SchedulerException
     */
    public void modify(String jobName, String jobDesc, String triggerType, String triggerExpression) throws SchedulerException {
        String triggerName = jobName + "Trigger";
        Trigger trigger = buildTrigger(triggerType, triggerName, TRIGGER_GROUP, triggerExpression, jobDesc);
        scheduler.rescheduleJob(new TriggerKey(triggerName, TRIGGER_GROUP), trigger);
    }

    /**
     * 删除定时器
     * @throws SchedulerException
     */
    public void remove(String jobName) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName, JOB_GROUP));
    }

    /**
     * 暂停定时器
     * @throws SchedulerException
     */
    public void pause(String jobName) throws SchedulerException {
        String triggerName = jobName + "Trigger";
        scheduler.pauseJob(new JobKey(jobName, JOB_GROUP));
        scheduler.pauseTrigger(new TriggerKey(triggerName, TRIGGER_GROUP));
    }

    /**
     * 恢复定时器
     * @throws SchedulerException
     */
    public void resume(String jobName) throws SchedulerException {
        String triggerName = jobName + "Trigger";
        scheduler.resumeJob(new JobKey(jobName, JOB_GROUP));
        scheduler.resumeTrigger(new TriggerKey(triggerName, TRIGGER_GROUP));
    }

    /**
     * 立即执行定时器
     * @throws SchedulerException
     */
    public void run(String jobName) throws SchedulerException {
        scheduler.triggerJob(new JobKey(jobName, JOB_GROUP));
    }

    public Trigger buildTrigger(String triggerType, String triggerName, String triggerGroup, String triggerExpression, String jobDesc) {
        Trigger trigger = null;
        switch (triggerType) {
            case "SIMPLE": {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerName, triggerGroup)
                        .withDescription(jobDesc)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(Integer.valueOf(triggerExpression))
                        .repeatForever())
                        .build();
            }
            break;
            case "CRON": {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerName, triggerGroup)
                        .withDescription(jobDesc)
                        .withSchedule(CronScheduleBuilder.cronSchedule(triggerExpression))
                        .build();
            }
            break;
        }
        return trigger;
    }

}
