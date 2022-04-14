package springold.bean;

/**
 * @Author：Weitj
 * @Description：
 * @Date： 2022/01/20 9:47
 * @Version 1.0
 */
public class ScheduleJobReq extends ScheduleJob implements PagePara{
    @Override
    public int getStartIndex() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }
}
