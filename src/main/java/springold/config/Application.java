package springold.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import springold.bean.Glossary;

/**
 * @Author：Weitj
 * @Description：
 * @Date： 2022/01/24 9:32
 * @Version 1.0
 */
@Component
public class Application implements ApplicationContextAware {
    public static   ApplicationContext context;
    public static   Boolean isInitialized;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        isInitialized = true ;
    }

    public static  <T> T getBean(String beanName,Class<T> clazz){
        return context.getBean(beanName,clazz);
    }
}
