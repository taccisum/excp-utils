package com.github.taccisum.excp;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.traceid.TraceIdGenerator;
import com.github.taccisum.excp.log.traceid.generator.ChainedTraceIdGenerator;
import com.github.taccisum.excp.remote.DingTalkRobotClientFacade;
import com.github.taccisum.excp.utils.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@ComponentScan(basePackageClasses = ExceptionUtilsAutoConfiguration.class)
public class ExceptionUtilsAutoConfiguration implements InitializingBean {
    @Autowired
    private ExceptionProperties properties;
    @Autowired
    private List<TraceIdGenerator> traceIdGenerators;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        IdUtils.setTraceIdGenerator(new ChainedTraceIdGenerator(traceIdGenerators));
    }
}
