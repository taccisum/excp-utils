package com.i61.apsara.excp;

import com.i61.apsara.excp.config.ExceptionProperties;
import com.i61.apsara.excp.remote.DingTalkRobotClientFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@ComponentScan(basePackageClasses = ExceptionUtilsAutoConfiguration.class)
public class ExceptionUtilsAutoConfiguration {
    @Autowired
    private ExceptionProperties properties;

    @Bean
    public DingTalkRobotClientFacade dingTalkRobotClientFacade() {
        String keys = properties.getDingTalk().getRobot().getKeys();
        String accessToken = properties.getDingTalk().getRobot().getAccessToken();

        if (StringUtils.isBlank(keys)) {
            throw new IllegalArgumentException(keys);
        }
        if (StringUtils.isBlank(accessToken)) {
            throw new IllegalArgumentException(accessToken);
        }

        return new DingTalkRobotClientFacade(
                accessToken,
                keys
        );
    }
}
