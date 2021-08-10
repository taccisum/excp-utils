package com.i61.apsara.excp;

import com.i61.apsara.excp.config.ExceptionProperties;
import com.i61.apsara.excp.remote.DingTalkRobotClientFacade;
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
        return new DingTalkRobotClientFacade(
                properties.getDingTalk().getRobot().getKeys(),
                properties.getDingTalk().getRobot().getAccessToken()
        );
    }
}
