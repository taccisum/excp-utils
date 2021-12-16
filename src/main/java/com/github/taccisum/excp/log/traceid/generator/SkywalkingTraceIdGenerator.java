package com.github.taccisum.excp.log.traceid.generator;

import com.github.taccisum.excp.log.traceid.TraceIdGenerator;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@Component
@ConditionalOnClass(TraceContext.class)
public class SkywalkingTraceIdGenerator implements TraceIdGenerator {
    @Override
    public String generate() {
        return TraceContext.traceId();
    }
}
