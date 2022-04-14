package springold.dao;

import org.springframework.stereotype.Component;
import springold.bean.ScheduleJob;
import springold.bean.ScheduleJobReq;

import java.util.List;
import java.util.Map;

/**
 * @Author：Weitj
 * @Description：定时任务job的具体数据执行层
 * @Date： 2022/01/20 9:57
 * @Version 1.0
 */
public interface QuartzJobDao {
    void addJob(ScheduleJob job);
    void deleteJob(int jobId);
    void updateJob(ScheduleJob job);
    List<ScheduleJob> jobsList();
    ScheduleJob getScheduleJobById(int id);

}
