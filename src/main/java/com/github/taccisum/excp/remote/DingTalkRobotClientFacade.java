package com.github.taccisum.excp.remote;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;

/**
 * 钉钉机器人对接工具类
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public class DingTalkRobotClientFacade {
    /**
     * 钉钉服务器地址
     */
    private static final String SERVER_URL = "https://oapi.dingtalk.com/robot/send";
    /**
     * 作为访问 DingTalk OAPI 凭证的 AccessToken
     */
    private String accessToken;
    /**
     * 用于匹配机器人的 "Custom Keywords" 的关键字
     */
    private String keys;

    public DingTalkRobotClientFacade(String accessToken, String keys) {
        this.accessToken = accessToken;
        this.keys = keys;
    }

    /**
     * 通过机器人发送 markdown 消息到群
     * <br/>
     * 示例：
     * 执行 sendMarkdown("告警信息", "# 系统异常\n内容内容内容")<br/>
     * 推送消息展示为：<br/>
     * 『 keys 』 告警信息
     * <br/>
     * 钉钉群展示为：<br/>
     * <h2>系统异常</h2>
     * 内容内容内容
     */
    public void sendMarkdown(String title, String markdown) {
        DingTalkClient client = new DefaultDingTalkClient(SERVER_URL);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.Markdown md = new OapiRobotSendRequest.Markdown();
        md.setTitle(String.format("『%s』 %s", keys, title));
        md.setText(markdown);
        request.setMsgtype("markdown");
        request.setMarkdown(md);
        try {
            OapiRobotSendResponse rsp = client.execute(request, accessToken);
            if (!rsp.isSuccess()) {
                throwError(rsp);
                return;
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 抛出异常
     */
    private void throwError(OapiRobotSendResponse rsp) {
        throw new RuntimeException(String.format("Fail to request DingTalk OAPI. Response is: \n%s", toStr(rsp)));
    }

    private String toStr(OapiRobotSendResponse rsp) {
        return new StringBuilder()
                .append("errcode: ").append(rsp.getErrcode()).append("\n")
                .append("errmsg: ").append(rsp.getErrmsg()).append("\n")
                .append("params: ").append(rsp.getParams())
                .toString()
                ;
    }

}
