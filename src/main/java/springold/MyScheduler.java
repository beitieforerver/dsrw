package springold;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/28
 */

public class MyScheduler {
    public void run() throws SchedulerException, InterruptedException {
        SchedulerFactory factory=new StdSchedulerFactory();
        Scheduler scheduler=factory.getScheduler();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        JobDetail job1=  JobBuilder.newJob(ColorJob.class).withIdentity("job1","group1").build();
        Date date = new Date();
        Date startTime = DateBuilder.nextGivenSecondDate(null, 13);
        Trigger trigger1 =  TriggerBuilder.newTrigger().withIdentity("trigger1","group1")
                .startAt(startTime).withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(1).withRepeatCount(4)).build();
        job1.getJobDataMap().put("count",1);
        job1.getJobDataMap().put("sex","boy");
        job1.getJobDataMap().put("total",1);
        job1.getJobDataMap().put("color","red");
        Date date1=scheduler.scheduleJob(job1,trigger1);

        JobDetail job2=  JobBuilder.newJob(ColorJob.class).withIdentity("job2","group1").build();
        Trigger trigger2 =  TriggerBuilder.newTrigger().withIdentity("trigger2","group1")
                .startAt(startTime).withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(1).withRepeatCount(4)).build();
        Trigger trigger3=TriggerBuilder.newTrigger().withIdentity("trigger3","group1")
                .startAt(startTime).withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(1).withRepeatCount(4)).build();;
        job2.getJobDataMap().put("count",1);
        job2.getJobDataMap().put("sex","girl");
        job2.getJobDataMap().put("total",1);
        job2.getJobDataMap().put("color","red");
        Date date2=scheduler.scheduleJob(job2,trigger2);
        System.out.println("当前时间==="+sdf.format(date));
        System.out.println("job1将在"+sdf.format(date1)+"执行");
        System.out.println("job2将在"+sdf.format(date2)+"执行");
        scheduler.start();
        TimeUnit.MINUTES.sleep(1);

        scheduler.shutdown();
    }
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        MyScheduler scheduler= new MyScheduler();
        scheduler.run();
    }
}
