package com.github.taccisum.excp.log.traceid;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass({Aspect.class, Scheduled.class})
@ConditionalOnProperty(prefix = ExceptionProperties.PREFIX + ".trace-id.enabled", name = "schedule", havingValue = "true", matchIfMissing = true)
public class ScheduledJobTraceIdAspect {
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void logPointCut() {
    }

    /**
     * 为 Job 输出的日志统一添加 trace id
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
