package springold.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springold.bean.ScheduleJob;
import springold.bean.ScheduleJobReq;
import springold.dao.QuartzJobDao;
import springold.support.PageableList;

import javax.annotation.Resource;
import java.util.List;

@Service( "quartzJobService" )
public class QuartzJobService
{
    @Autowired
    private QuartzJobDao quartzJobDao;


    /**
     * 获取所有的定时任务记录信息
     * @return
     */
    public List<ScheduleJob> getAllJobs()
    {
        return this.quartzJobDao.jobsList() ;
    }


    /**
     * 根据id获取任务记录
     * @param id
     * @return
     */
    public ScheduleJob getScheduleJobById( int id )
    {
        return(this.quartzJobDao.getScheduleJobById(  id ) );
    }


    /**
     * 插入一条定时任务记录
     * @param job
     */
    public void inserJob( ScheduleJob job )
    {
        this.quartzJobDao.addJob( job );
    }


    /**
     * 更新一条定时任务记录
     * @param job
     */
    public void updateJob( ScheduleJob job )
    {
        this.quartzJobDao.updateJob( job );
    }


   /**
    * @Description: 删除一条定时任务记录
    * @Author: Weitj
    * @Date: 2022/01/20 18:03
     * @param jobId 根据jobId进行删除
    */
    public void deleteJob( int jobId)
    {
        this.quartzJobDao.deleteJob( jobId );
    }


    /**
     * 分页获取定时任务记录信息
     * @return
     */
    public PageableList<ScheduleJob> getJobsByPage(ScheduleJobReq jobReq )
    {
        List<ScheduleJob> scheduleJobs = quartzJobDao.jobsList();
        return new PageableList<>(scheduleJobs ,jobReq.getStartIndex(),jobReq.getPageSize(),jobReq.getItemCount());
    }


    /**
     * 分页获取定时任务执行记录信息
     * @return
     */
//    public PageableList<ScheduleJobItem> getJobItemsByPage( Integer jobId, ScheduleJobReq jobReq )
//    {
//        PaginationBean<ScheduleJobItem> pb    = new PaginationBean<ScheduleJobItem>( jobReq.getCurrent(), 0, jobReq.getPageSize() );
//        Map<String, Object>        map    = new HashMap<String, Object>();
//        map.put( "jobId", jobId );
//        map.put( "page", pb );
//        return(this.quartzJobDao.queryForListPageByMap( JOBITEM_LIST_PAGE, map ) );
//    }


    /**
     * 插入一条定时任务执行记录信息
     * @param jobItem
     */
//    @Transactional( propagation = Propagation.REQUIRED )
//    public void inserJobItem( ScheduleJobItem jobItem )
//    {
//        this.quartzJobDao.insertData( JOBITEM_INSERT, jobItem );
//    }


    /**
     * 根据ID获取一条定时任务执行记录信息
     * @param id
     * @return
     */
//    public ScheduleJobItem getScheduleJobItemById( int id )
//    {
//        return(this.quartzJobDao.query( JOBITEM_SELETE_BYID, id ) );
//    }
}