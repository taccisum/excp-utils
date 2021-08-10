package com.i61.apsara.excp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@Data
@Component
@ConfigurationProperties("apsara.excp")
public class ExceptionProperties {
    /**
     * 钉钉相关配置
     */
    private DingTalk dingTalk = new DingTalk();

    @Data
    public static class DingTalk {
        /**
         * 钉钉告警相关配置
         */
        private Alarm alarm = new Alarm();
        /**
         * 钉钉机器人相关配置
         */
        private Robot robot = new Robot();

        @Data
        public static class Robot {
            private String keys;
            private String accessToken;
        }

        @Data
        public static class Alarm {
            /**
             * 是否开启钉钉告警
             */
            private Boolean enabled = false;
        }
    }
}
