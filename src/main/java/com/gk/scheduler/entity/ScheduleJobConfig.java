package com.gk.scheduler.entity;

import java.util.Date;

public class ScheduleJobConfig {

    /**
     * 任务状态 - 未初始化：无触发器
     */
    public static final Integer STATUS_NOT_INIT = 0;
    /**
     * 任务状态 - 正常运行：WAITING、ACQUIRED、BLOCKED
     */
    public static final Integer STATUS_RUNNING = 1;
    /**
     * 任务状态 - 运行异常：COMPLETE
     */
    public static final Integer STATUS_EXCEPTION = 2;
    /**
     * 任务状态 - 暂停运行：PAUSED、PAUSED_BLOCKED
     */
    public static final Integer STATUS_STOP = 3;
    /**
     * 任务状态 - 已被移除：无触发器
     */
    public static final Integer STATUS_REMOVED = 4;
    /**
     * 任务状态 - 配置错误：找不到相应的Job.
     */
    public static final Integer STATUS_ERROR = 5;

    /**
     * 触发器状态 - 等待中：触发器正在等待到时间，来被出发。比如说当前时间为20:00，下次触发时间为20:05，则此时，该定时器处于该状态
     */
    public static final String TRIGGER_STATE_WAITING = "WAITING";
    /**
     * 触发器状态 - 获得中：当到达触发时间时，定时器会尝试去获得`WAITING`状态的触发器，并设置成当前状态
     */
    public static final String TRIGGER_STATE_ACQUIRED = "ACQUIRED";
    /**
     * 触发器状态 - 执行中：该状态主要用于集群时，标记某个触发器正在某个节点的定时器正在执行种。触发器本身不会设成该状态
     */
    public static final String TRIGGER_STATE_EXECUTING = "EXECUTING";
    /**
     * 触发器状态 - 完成：触发器已经完成。处于当前状态后，触发器再也不会触发。
     */
    public static final String TRIGGER_STATE_COMPLETE = "COMPLETE";
    /**
     * 触发器状态 - 阻塞中：触发器触发，任务在执行中，这时，触发器不再被触发，直到任务完成。
     */
    public static final String TRIGGER_STATE_BLOCKED = "BLOCKED";
    /**
     * 触发器状态 - 错误：触发器本身配置有问题.比如说无法获得任务Job
     */
    public static final String TRIGGER_STATE_ERROR = "ERROR";
    /**
     * 触发器状态 - 暂停：触发器被暂停。。处于当前状态后，触发器再也不会触发。
     */
    public static final String TRIGGER_STATE_PAUSED = "PAUSED";
    /**
     * 触发器状态 - 暂停 + 阻塞：暂时阻塞状态中的任务。任务不会马上停止，而是将触发器设置成`PAUSED_BLOCKED`，直到任务完成后，设置成`PAUSED`
     */
    public static final String TRIGGER_STATE_PAUSED_BLOCKED = "PAUSED_BLOCKED";
    /**
     * 触发器状态 - 删除：该状态实际不存在。
     */
    public static final String TRIGGER_STATE_DELETED = "DELETED";

    /**
     * 监控状态--开启
     */
    public static final Integer MONITOR_STATUS_OPEN = 1;
    /**
     * 监控状态--关闭
     */
    public static final Integer MONITOR_STATUS_CLOSE = 0;

    /**
     * 触发类型 - 时间间隔，单位：秒
     */
    public static final String TRIGGER_TYPE_SIMPLE = "Simple";
    /**
     * 触发类型 - Cron表达式
     */
    public static final String TRIGGER_TYPE_CRON = "Cron";

    /**
     * 触发器集群 - 默认
     */
    public static final String DEFAULT_GROUP = "DEFAULT";

    /**
     * 编号
     */
    private Integer id;
    /**
     * 集群名
     */
    private String scheduleName;
    /**
     * 任务名
     */
    private String jobNickname;
    /**
     * 映射服务名
     */
    private String jobName;
    /**
     * 任务状态
     */
    private Integer status;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 触发器名字
     */
    private String triggerName;
    /**
     * 触发器分组
     */
    private String triggerGroup;
    /**
     * 触发器类型
     */
    private String triggerType;
    /**
     * 触发器表达式
     */
    private String triggerExpression;
    /**
     * 任务描述
     */
    private String jobDesc;
    /**
     * 执行后备注
     */
    private String finishMemo;
    /**
     * 执行后异常
     */
    @Deprecated
    private String finishException;
    /**
     * 最后一次开始时间.该字段在任务结束后记录
     */
    private Date startTime;
    /**
     * 最后一次完成时间.该字段在任务结束后记录（即使发生异常，则记录）
     */
    private Date endTime;

    // ========== 监控相关字段 ==========
    /**
     * 监控状态
     */
    private Integer monitorStatus;
    /**
     * 执行时间--限制（秒）
     */
    private Integer limitTime;
    /**
     * 上次触发时间
     */
    private Date prevTime;
    /**
     * 下次触发时间
     */
    private Date nextTime;

    // ========== 非数据库字段 ==========
    /**
     * 上次触发时间，来自SCHE_TRIGGERS表
     */
    private Long PREV_FIRE_TIME;
    /**
     * 下次触发时间，来自SCHE_TRIGGERS表.在触发器状态从`ACQUIRED`变成`BLOCKED`时，就会更新PREV_FIRE_TIME、NEXT_FIRE_TIME。然后定时任务才正式开始执行
     */
    private Long NEXT_FIRE_TIME;



    /**
     * 暂停状态  1:隔N分钟提醒  2：M天内不提醒 3:删除状态
     */
    private Integer pauseStatus;

    public Integer getPauseStatus() {
        return pauseStatus;
    }

    public void setPauseStatus(Integer pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public Long getPREV_FIRE_TIME() {
        return PREV_FIRE_TIME;
    }

    public void setPREV_FIRE_TIME(Long PREV_FIRE_TIME) {
        this.PREV_FIRE_TIME = PREV_FIRE_TIME;
    }

    public Long getNEXT_FIRE_TIME() {
        return NEXT_FIRE_TIME;
    }

    public void setNEXT_FIRE_TIME(Long NEXT_FIRE_TIME) {
        this.NEXT_FIRE_TIME = NEXT_FIRE_TIME;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getJobNickname() {
        return jobNickname;
    }

    public void setJobNickname(String jobNickname) {
        this.jobNickname = jobNickname;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTriggerExpression() {
        return triggerExpression;
    }

    public void setTriggerExpression(String triggerExpression) {
        this.triggerExpression = triggerExpression;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public static Integer getStatusNotInit() {
        return STATUS_NOT_INIT;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Date getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Date prevTime) {
        this.prevTime = prevTime;
    }

    public String getFinishMemo() {
        return finishMemo;
    }

    public void setFinishMemo(String finishMemo) {
        this.finishMemo = finishMemo;
    }
}
