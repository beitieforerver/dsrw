package jdkR;

import org.junit.Before;
import org.junit.Test;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/28
 */
public class TimerServiceImplTest {
    TimerServiceImpl timerService;
    @Before
    public void before(){
        timerService  = new TimerServiceImpl();
    }
    @Test
    public void scheduleTimerTask_Date_PeriodTest(){
        timerService.scheduleTimerTask_Date();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void printTest(){
        timerService.println("chinese");
    }

}
