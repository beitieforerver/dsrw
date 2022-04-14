package springold;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/28
 */
@PersistJobDataAfterExecution   //每次执行后持久化dataMap便于下次调用数据使用
@DisallowConcurrentExecution  //不允许并发执行
public class SimpleJobQuartz implements Job {
    private int _counter = 1;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap=context.getJobDetail().getJobDataMap();

        System.out.println("--triggerKey---"+context.getTrigger().getKey()+"---\n"
        + "--triggerKey---"+context.getJobDetail().getKey()
                );

        int count=dataMap.getInt("count");
        String sex=dataMap.getString("sex");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        String time=sdf.format(Calendar.getInstance().getTime());
        System.out.println("--currenttime###----"+time +"--- sex ---"+ sex +"--- count ---"+ count +"--this-" + this.toString());
        count++;
        dataMap.put("count",count);
        this._counter+=1;
    }
}
