package com.i61.apsara.excp.aspect;

import com.i61.apsara.excp.log.LogMDCHelper;
import com.i61.apsara.excp.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@Slf4j
public abstract class SpringScheduledJobTraceIdAspect {
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void schedulingJobMethods() {
    }

    /**
     * 为 Job 输出的日志统一添加 trace id
     */
    @Around("schedulingJobMethods()")
    public Object setEventBus(ProceedingJoinPoint point) throws Throwable {
        try {
            LogMDCHelper.setTraceId(IdUtils.uuid(10));
            return point.proceed();
        } finally {
            LogMDCHelper.removeTraceId();
        }
    }
}
