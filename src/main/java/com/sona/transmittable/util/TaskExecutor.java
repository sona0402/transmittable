package com.sona.transmittable.util;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 异步任务执行线程池
 *
 * @author renfakai
 * @since 2019/12/27
 */
public final class TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    private static volatile ExecutorService executor = null;

    private static void init() {
        String logTitle = "初始化异步任务执行线程池";
        Runtime runtime = Runtime.getRuntime();
        if (null == executor) {
            synchronized (TaskExecutor.class) {
                if (null == executor) {
                    LOGGER.info(String.format("%s, 开始...", logTitle));
                    int corePoolSize = runtime.availableProcessors();
                    int maximumPoolSize = corePoolSize * 2;
                    long keepAliveTime = 180;
                    TimeUnit unit = TimeUnit.SECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = r -> {
                        Thread t = new Thread(r);
                        t.setName("TaskExecutor-" + t.getId());
                        t.setPriority(Thread.MIN_PRIORITY);
                        return t;
                    };
                    executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);

                    // wrap executor  to  ExecutorServiceTtlWrapper
                    executor = TtlExecutors.getTtlExecutorService(executor);
                    LOGGER.info(String.format("%s, 完成. corePoolSize: %s, maximumPoolSize: %s, keepAliveTime: %s, unit: %s",
                            logTitle, corePoolSize, maximumPoolSize, keepAliveTime, unit));

                }
            }
        }
    }

    public static void submit(Runnable task) {
        init();
        executor.submit(task);
    }

    public static boolean shutdown() {
        if (null == executor) {
            return true;
        }

        executor.shutdown();

        try {
            return executor.awaitTermination(3, TimeUnit.MINUTES);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
