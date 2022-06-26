package info.akaki.usage.config;

import info.akaki.usage.exception.ServiceDeliveryAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ServiceDeliveryAsyncConfig implements AsyncConfigurer {

    private static final int THREAD_POOL_SIZE = 10;

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(THREAD_POOL_SIZE);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new ServiceDeliveryAsyncExceptionHandler();
    }
}
