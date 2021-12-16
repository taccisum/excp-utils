package com.github.taccisum.excp.log.traceid.generator;

import com.github.taccisum.excp.log.traceid.TraceIdGenerator;
import com.github.taccisum.excp.utils.IdUtils;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@Component
public class SimpleTraceIdGenerator implements TraceIdGenerator {
    @Override
    public String generate() {
        return IdUtils.uuid(10);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
