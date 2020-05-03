package com.github.zhengframework.job.jobs;

import com.github.zhengframework.job.Job;
import java.util.concurrent.CountDownLatch;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractJob extends Job {

    private final CountDownLatch latch;

    public AbstractJob(int count) {
        super();
        latch = new CountDownLatch(count);
    }

    public AbstractJob(int count, String groupName) {
        super(groupName);
        latch = new CountDownLatch(count);
    }

    @Override
    public void doJob(JobExecutionContext context) throws JobExecutionException {
        latch.countDown();
    }

    public CountDownLatch latch() {
        return latch;
    }
}
