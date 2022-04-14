package springold.bean;

import springold.base.version.VersionableBean;
import springold.utils.CodeMapUtils;

/**
 * 定时任务封装类
 * @author   xiaohe
 */
public class ScheduleJob extends VersionableBean {

    public static final byte STATUS_待执行 = 0 ;
    public static final byte STATUS_执行中 = 1 ;
    public static final byte STATUS_暂停中 = 2 ;
    public static final byte STATUS_已注销 = 3 ;

    /** 任务id */
    private int jobId;

    /** 任务名称 */
    private String jobName;

    /** 任务分组 */
    private String jobGroup;

    /** 任务状态 和status对应 */
    private String jobStatus;

    private String jobStatusName;

    /** 任务运行时间表达式  */
    private String cronExpression;

    /** 任务执行类 */
    private String beanClass;

    /** 任务执行方法 */
    private String executeMethod;

    /** 任务创建时间 */
    private String createTime;

    /** 任务更新时间 */
    private String updateTime;

    /** 任务描述 */
    private String jobDesc;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
        this.jobStatusName = CodeMapUtils.getCodeName(ScheduleJob.class,"STATUS",getJobStatus());

    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", beanClass='" + beanClass + '\'' +
                ", executeMethod='" + executeMethod + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                '}';
    }
}