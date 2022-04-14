package springold.factory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author betieforever
 * @description √Ë ˆ
 * @date 2022/1/21
 */
public class MyTestJob3 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(this.getClass().getName()+":::"+sdf.format(new Date()));
    }
}
