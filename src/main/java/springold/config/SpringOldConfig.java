package springold.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/28
 */
@Configuration
public class SpringOldConfig {
    @Bean
    public SimpleTrigger getSimpleTrigger(){
        SimpleTrigger trigger=new SimpleTrigger() {
            @Override//重复次数
            public int getRepeatCount() {
                return 1;
            }

            @Override //执行时间间隔
            public long getRepeatInterval() {
                return 0;
            }

            @Override
            public int getTimesTriggered() {
                return 0;
            }

            @Override
            public TriggerBuilder<SimpleTrigger> getTriggerBuilder() {
                return null;
            }

            @Override
            public TriggerKey getKey() {
                return null;
            }

            @Override
            public JobKey getJobKey() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getCalendarName() {
                return null;
            }

            @Override
            public JobDataMap getJobDataMap() {
                return null;
            }

            @Override
            public int getPriority() {
                return 0;
            }

            @Override
            public boolean mayFireAgain() {
                return false;
            }

            @Override
            public Date getStartTime() {
                return null;
            }

            @Override
            public Date getEndTime() {
                return null;
            }

            @Override
            public Date getNextFireTime() {
                return null;
            }

            @Override
            public Date getPreviousFireTime() {
                return null;
            }

            @Override
            public Date getFireTimeAfter(Date date) {
                return null;
            }

            @Override
            public Date getFinalFireTime() {
                return null;
            }

            @Override
            public int getMisfireInstruction() {
                return 0;
            }

            @Override
            public ScheduleBuilder<? extends Trigger> getScheduleBuilder() {
                return null;
            }

            @Override
            public int compareTo(Trigger trigger) {
                return 0;
            }
        };
        return trigger;
    }
    @Bean
    public CronTrigger getCronTrigger(){
        CronTrigger cronTrigger = new CronTrigger() {
            @Override
            public String getCronExpression() {
                return null;
            }

            @Override
            public TimeZone getTimeZone() {
                return null;
            }

            @Override
            public String getExpressionSummary() {
                return null;
            }

            @Override
            public TriggerBuilder<CronTrigger> getTriggerBuilder() {
                return null;
            }

            @Override
            public TriggerKey getKey() {
                return null;
            }

            @Override
            public JobKey getJobKey() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getCalendarName() {
                return null;
            }

            @Override
            public JobDataMap getJobDataMap() {
                return null;
            }

            @Override
            public int getPriority() {
                return 0;
            }

            @Override
            public boolean mayFireAgain() {
                return false;
            }

            @Override
            public Date getStartTime() {
                return null;
            }

            @Override
            public Date getEndTime() {
                return null;
            }

            @Override
            public Date getNextFireTime() {
                return null;
            }

            @Override
            public Date getPreviousFireTime() {
                return null;
            }

            @Override
            public Date getFireTimeAfter(Date afterTime) {
                return null;
            }

            @Override
            public Date getFinalFireTime() {
                return null;
            }

            @Override
            public int getMisfireInstruction() {
                return 0;
            }

            @Override
            public ScheduleBuilder<? extends Trigger> getScheduleBuilder() {
                return null;
            }

            @Override
            public int compareTo(Trigger other) {
                return 0;
            }
        };
        return cronTrigger;
    }
}
