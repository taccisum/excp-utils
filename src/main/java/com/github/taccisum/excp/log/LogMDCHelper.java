package com.github.taccisum.excp.log;

import com.github.taccisum.excp.config.ExceptionProperties;
import org.slf4j.MDC;

import java.util.Optional;

/**
 * logger MDC helper
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public abstract class LogMDCHelper {
    private static ExceptionProperties properties;
    public static String TRACE_ID_DEF_KEY = "Trace-Id";

    public static void setProperties(ExceptionProperties properties) {
        LogMDCHelper.properties = properties;
    }

    public static void setTraceId(String traceId) {
        MDC.put(getTraceIdKey(), traceId);
    }

    public static String getTraceId() {
        return MDC.get(getTraceIdKey());
    }

    public static void removeTraceId() {
        MDC.remove(getTraceIdKey());
    }


    private static String getTraceIdKey() {
        if (properties == null) {
            return TRACE_ID_DEF_KEY;
        }
        return Optional.ofNullable(properties.getTraceId().getKey()).orElse(TRACE_ID_DEF_KEY);
    }
}
