package springold;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class JobStateExample
{
    public void run()
            throws Exception
    {
        System.out.println("------- 初始化 -------------------");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println("------- 初始化完成 --------");

        System.out.println("------- 向Scheduler加入Job ----------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        Date startTime = DateBuilder.nextGivenSecondDate(null, 10);

        JobDetail job1 = JobBuilder.newJob(SimpleJobQuartz.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger1 = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(4))
                .build();

        job1.getJobDataMap().put("color", "Green");
        job1.getJobDataMap().put("count", 1);
        job1.getJobDataMap().put("total", 1);
        job1.getJobDataMap().put("sex", "boy");

        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        System.out.println(job1.getKey() + "  将在:  " + dateFormat.format(scheduleTime1) + " 运行，重复 " + trigger1.getRepeatCount() + " 次,每 " + trigger1.getRepeatInterval() / 1000L + " 秒执行一次");

        JobDetail job2 = JobBuilder.newJob(SimpleJobQuartz.class).withIdentity("job2", "group1").build();
        SimpleTrigger trigger2 = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(4))
                .build();

        job2.getJobDataMap().put("color", "Red");
        job2.getJobDataMap().put("count", 1);
        job2.getJobDataMap().put("total", 1);
        job2.getJobDataMap().put("sex", "girl");

        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        System.out.println(job2.getKey().toString() + "  将在:  " + dateFormat.format(scheduleTime2) + " 运行，重复 " + trigger2.getRepeatCount() + " 次,每 " + trigger2.getRepeatInterval() / 1000L + " 秒执行一次");

        System.out.println("------- 开始Scheduler ----------------");

        sched.start();

        System.out.println("------- Scheduler调用job结束 -----------------");

        System.out.println("------- 等待60秒... -------------");
        try
        {
            Thread.sleep(60000L);
        }
        catch (Exception e)
        {
        }

        System.out.println("------- 关闭Scheduler ---------------------");

        sched.shutdown(true);

        System.out.println("------- 关闭完成 -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args)
            throws Exception
    {
        JobStateExample example = new JobStateExample();
        example.run();
    }
}