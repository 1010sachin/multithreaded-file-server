package com.sachin.app;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

/**
 * Class that provides custom name to the threads to be used by the
 * {@link java.util.concurrent.ExecutorService}
 */
public class CustomThreadFactory implements ThreadFactory {
    private final LongAdder count = new LongAdder();
    private final String threadPrefix;

    public CustomThreadFactory(final String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }
    @Override
    public Thread newThread(Runnable r) {
        count.increment();
        final String name = String.format("%s-%d", threadPrefix,
                count.sum());
        final Thread thread = new Thread(r, name);
        thread.setDaemon(true);
        return thread;
    }
}
