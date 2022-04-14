package jdkR;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/28
 */
class TimerServiceImpl {

    static void println(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args) {
        TimerServiceImpl timerService = new TimerServiceImpl();
        timerService.scheduleTimerTask_Date();
    }
    public void scheduleTimerTask_Date(){
        for (int i = 0; i < 10; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    println(Thread.currentThread().getName() + " run ");
                }
            }, new Date(System.currentTimeMillis() + 2000));
        }
    }
    public void scheduleTimerTask_Date_Period(){
        for (int i = 0; i < 2; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    println(Thread.currentThread().getName() + " run ");
                }
            }, new Date(System.currentTimeMillis() + 2000),3000);
        }
    }
}
