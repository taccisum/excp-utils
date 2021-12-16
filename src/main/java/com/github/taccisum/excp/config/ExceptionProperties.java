package com.github.taccisum.excp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@Data
@Component
@ConfigurationProperties(ExceptionProperties.PREFIX)
public class ExceptionProperties {
    public static final String PREFIX = "excp";

    /**
     * 钉钉相关配置
     */
    private DingTalk dingTalk = new DingTalk();
    /**
     * 追踪 id 相关配置
     */
    private TraceId traceId = new TraceId();

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
            private String title;
        }
    }

    @Data
    public static class TraceId {
        /**
         * 各种场景下的 trace id 开关控制
         */
        private Enabled enabled = new Enabled();
        /**
         * 存储 trace id 所使用的 key
         */
        private String key = "Trace-Id";

        @Data
        public static class Enabled {
            /**
             * Java Servlet
             */
            private Boolean servlet = true;
            /**
             * Spring Scheduled Job
             */
            private Boolean scheduled = true;
            /**
             * Rabbit MQ Listener
             */
            private Boolean rabbit = true;
        }
    }
}
