package springold;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.stereotype.Component;
import springold.bean.ScheduleJob;
import springold.factory.QuartzJobFactory;
import springold.service.QuartzJobService;

import javax.annotation.Resource;
import java.util.List;


/**
 * 提供Job任务相关的方法
 *
 * @author xiaohe
 */
public class JobMethod {
    @Resource(name = "schedulerFactoryBean")
    private Scheduler scheduler;

    @Resource(name = "quartzJobService")
    private QuartzJobService quartzJobService;

    private static Log log = LogFactory.getLog(JobMethod.class);

    public static ClassLoader classLoader;

    /**
     * 任务框架初始化方法
     *
     * @throws
     */
    public void init() throws SchedulerException{
        classLoader = this.getClass().getClassLoader();
        /* 从数据库获得所有的任务信息记录 */
        List<ScheduleJob> jobList = quartzJobService.getAllJobs();
        System.out.println("初始化方法执行");
        if (jobList != null && !jobList.isEmpty()) {
            for (ScheduleJob scheduleJob : jobList) {
                addOrUpdateJob(scheduleJob);
            }
        }
    }


    private Class<Job> getClassJob(String name) {
        try {
            Class<Job> jobClass = (Class<Job>) classLoader.loadClass(name);
            return jobClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("Task pause failed.", e);
        }
    }

    public void addOrUpdateJob(ScheduleJob scheduleJob) throws SchedulerException {
        Class<Job> jobClass = getClassJob(scheduleJob.getBeanClass());
        CronTrigger cronTrigger = null;
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob
                .getJobName(), scheduleJob.getJobGroup());
        try {
            cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == cronTrigger) {
                JobDetail jobDetail = JobBuilder.newJob(
                        jobClass).withIdentity(
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup()).build();

                jobDetail.getJobDataMap().put("scheduleJob",
                        scheduleJob);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                        .cronSchedule(scheduleJob.getCronExpression());

                cronTrigger = TriggerBuilder.newTrigger().withIdentity(
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup()).withSchedule(
                        scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, cronTrigger);
            } else {
                /* Trigger已存在，那么更新相应的定时设置 */
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                        .cronSchedule(scheduleJob.getCronExpression());

                /* 按新的cronExpression表达式重新构建trigger */
                cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(
                                triggerKey).withSchedule(scheduleBuilder)
                        .build();

                /* 按新的trigger重新设置job执行 */
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (SchedulerException e) {
            log.error("Task init failed.", e);
        }

    }



    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        if(jobKey != null ){

        }
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("Task resume failed.", e);
        }
    }


    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("Task delete failed.", e);
        }
    }


    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runJobNow(ScheduleJob scheduleJob) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("Task run failed.", e);
        }
    }


    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
                scheduleJob.getJobGroup());
        /* 获取trigger，即在spring配置文件中定义的 bean id="schedulerFactoryBean" */
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        /* 表达式调度构建器 */
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
                .getCronExpression());
        /*按新的cronExpression表达式重新构建trigger */
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        /*按新的trigger重新设置job执行 */
        scheduler.rescheduleJob(triggerKey, trigger);
    }


    /**
     * 判断表达式是否可用
     *
     * @param cron
     * @return
     * @throws
     */
    public boolean checkCron(String cron) {
        try {
            CronScheduleBuilder.cronSchedule(cron);
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }
}