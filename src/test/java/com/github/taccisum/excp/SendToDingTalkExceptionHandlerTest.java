package com.github.taccisum.excp;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.remote.DingTalkRobotClientFacade;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@RunWith(SpringRunner.class)
@Import(SendToDingTalkExceptionHandler.class)
public class SendToDingTalkExceptionHandlerTest {
    @Autowired
    private SendToDingTalkExceptionHandler handler;
    @MockBean
    private ExceptionProperties properties;

    @After
    public void tearDown() throws Exception {
        LogMDCHelper.removeTraceId();
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public DingTalkRobotClientFacade dingTalkRobotClientFacade() {
            return new DingTalkRobotClientFacade(null, null) {
                @Override
                public void sendMarkdown(String title, String markdown) {
                    System.out.println(title);
                    System.out.println(markdown);
                }
            };
        }
    }

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    public void name() {
        ExceptionProperties.DingTalk dingTalk = new ExceptionProperties.DingTalk();
        dingTalk.getAlarm().setEnabled(true);
        dingTalk.getAlarm().setTitle("excp-utils 单测告警");

        when(properties.getDingTalk()).thenReturn(dingTalk);

        RuntimeException e = new RuntimeException("测试");
        String extraInfo = "hello";
        String traceId = "my_trace_id";
        LogMDCHelper.setTraceId(traceId);
        handler.handle(e, extraInfo);
        assertThat(capture.toString()).contains(
                "excp-utils 单测告警",
                "系统异常",
                "错误信息：测试",
                "环境信息：default",
                "堆栈信息：" + e.getClass().getName(),
                "Trace ID：" + traceId,
                "额外信息：" + extraInfo);
        capture.reset();
    }
}