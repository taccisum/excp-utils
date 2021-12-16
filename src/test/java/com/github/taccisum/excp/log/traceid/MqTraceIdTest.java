package com.github.taccisum.excp.log.traceid;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.log.traceid.generator.SimpleTraceIdGenerator;
import com.github.taccisum.excp.utils.IdUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy
@Import({RabbitTraceIdAspect.class, SimpleTraceIdGenerator.class, MqTraceIdTest.MyListener.class})
public class MqTraceIdTest {
    @Autowired
    private MyListener listener;
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
        listener.foo();
        assertThat(LogMDCHelper.getTraceId()).isBlank();
    }

    public static class MyListener {
        @RabbitListener
        public void foo() {
            assertThat(LogMDCHelper.getTraceId()).isNotBlank();
        }
    }
}
