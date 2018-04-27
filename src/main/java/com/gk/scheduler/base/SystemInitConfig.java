package com.gk.scheduler.base;


import com.gk.scheduler.schedulerManage.SchedulerManageService;
import com.gk.scheduler.util.BeanManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemInitConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private StdScheduler scheduler;

    public void init() {
        // 子类实现
        try {

            BeanManager.getBean(SchedulerManageService.class).init();
            // 子类实现
            startScheduler();
            // 输出结果
        } catch (Exception e) {
            logger.error("[init][初始化报异常：{}]", ExceptionUtils.getStackTrace(e));
        }
    }

    private void startScheduler() throws SchedulerException {
        long now = System.currentTimeMillis();
        logger.info("初始StdScheduler开始...");
        scheduler = BeanManager.getBean("quartzScheduler");
        scheduler.start();
        logger.info("初始StdScheduler完成...消耗：{}毫秒!", System.currentTimeMillis() - now);
    }

    private void destroyScheduler() {
        long now = System.currentTimeMillis();
        logger.info("销毁StdScheduler开始...");
        scheduler.shutdown(true);
        logger.info("销毁StdScheduler完成...消耗：{}毫秒!", System.currentTimeMillis() - now);
    }

    public void destroy() {
        try {
            destroyScheduler();
        } catch (Exception e) {
            logger.error("[destroy][销毁报异常：{}]", ExceptionUtils.getStackTrace(e));
        }
    }
}
