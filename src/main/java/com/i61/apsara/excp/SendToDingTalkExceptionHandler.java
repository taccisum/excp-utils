package com.i61.apsara.excp;


import com.i61.apsara.excp.config.ExceptionProperties;
import com.i61.apsara.excp.remote.DingTalkRobotClientFacade;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用于将错误信息发送至钉钉群的 exception handler
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@Component
public class SendToDingTalkExceptionHandler {
    @Autowired
    private ExceptionProperties properties;
    @Autowired
    private DingTalkRobotClientFacade dingTalkRobotClient;

    private static final int MAX_DINGTALK_CONTENT_LENGTH = 1000;

    public void handle(Throwable e) {
        handle(e, null);
    }

    /**
     * @param e         异常
     * @param extraInfo 额外信息
     */
    public void handle(Throwable e, String extraInfo) {
        boolean shouldSend = true;

        if (!properties.getDingTalk().getAlarm().getEnabled()) {
            // 未开启钉钉告警 不发
            shouldSend = false;
        } else if (e instanceof BaseBizException) {
            // 业务异常不发
            shouldSend = false;
        }

        if (shouldSend) {
            String title = properties.getDingTalk().getAlarm().getTitle();
            title = title == null ? "监控告警" : title;
            dingTalkRobotClient.sendMarkdown(
                    title,
                    String.format("# 系统异常（%s）\n" +
                                    "错误信息：%s  \n" +
                                    "堆栈信息：%s  \n" +
                                    "额外信息：%s",
                            title,
                            e.getMessage(),
                            ExceptionUtils.getStackTrace(e).substring(0, MAX_DINGTALK_CONTENT_LENGTH) + "......",
                            extraInfo
                    )
            );
        }
    }
}
