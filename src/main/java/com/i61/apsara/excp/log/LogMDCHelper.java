package com.i61.apsara.excp.log;

import org.slf4j.MDC;

/**
 * logger MDC helper
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public abstract class LogMDCHelper {
    public static final String TRACE_ID_KEY = "Trace-Id";

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID_KEY);
    }

    public static void removeTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }
}
