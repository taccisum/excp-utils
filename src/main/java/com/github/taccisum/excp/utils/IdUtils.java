package com.github.taccisum.excp.utils;

import com.github.taccisum.excp.log.traceid.TraceIdGenerator;

import java.util.UUID;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public abstract class IdUtils {
    private static TraceIdGenerator traceIdGenerator;

    public static void setTraceIdGenerator(TraceIdGenerator traceIdGenerator) {
        IdUtils.traceIdGenerator = traceIdGenerator;
    }

    public static String uuid() {
        return uuid(32);
    }

    public static String uuid(int length) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }

    public static String traceId() {
        return traceIdGenerator.generate();
    }
}
