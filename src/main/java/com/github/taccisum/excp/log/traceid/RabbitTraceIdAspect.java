package com.github.taccisum.excp.log.traceid;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass({Aspect.class, RabbitListener.class})
@ConditionalOnProperty(prefix = ExceptionProperties.PREFIX + ".trace-id.enabled", name = "rabbit", havingValue = "true", matchIfMissing = true)
public class RabbitTraceIdAspect {
    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void logPointCut() {
    }

    /**
     * 为MQ事件增加 Trace ID
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            LogMDCHelper.setTraceId(IdUtils.traceId());
            return point.proceed();
        } finally {
            LogMDCHelper.removeTraceId();
        }
    }
}
