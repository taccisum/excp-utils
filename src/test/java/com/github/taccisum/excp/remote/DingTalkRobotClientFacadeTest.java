package com.github.taccisum.excp.remote;

import org.junit.Test;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public class DingTalkRobotClientFacadeTest {
    private DingTalkRobotClientFacade client = new DingTalkRobotClientFacade(
            "4eeaec08d6bc696a581f74e09c33edd30cf2de9231ba5c569e45013bad845ff1",
            "dev"
    );

    @Test
    public void sendMarkdown() {
        client.sendMarkdown("excp-utils 测试告警信息", "## 系统异常\n内容内容内容内容  \n无视这条来自单元测试的消息");
    }
}