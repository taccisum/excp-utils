package com.github.taccisum.excp.log.traceid;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.log.traceid.generator.SimpleTraceIdGenerator;
import com.github.taccisum.excp.utils.IdUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy
@Import({ScheduledJobTraceIdAspect.class, SimpleTraceIdGenerator.class, JobTraceIdTest.MyJob.class})
public class JobTraceIdTest {
    @Autowired
    private MyJob job;
    @Autowired
    private SimpleTraceIdGenerator generator;

    @Before
    public void setUp() throws Exception {
        IdUtils.setTraceIdGenerator(generator);
        LogMDCHelper.setProperties(new ExceptionProperties());
    }

    @Test
    public void index() {
        assertThat(LogMDCHelper.getTraceId()).isBlank();
        job.foo();
        assertThat(LogMDCHelper.getTraceId()).isBlank();
    }

    public static class MyJob {
        @Scheduled
        public void foo() {
            assertThat(LogMDCHelper.getTraceId()).isNotBlank();
        }
    }
}
