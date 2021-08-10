package com.i61.apsara.excp.utils;

import java.util.UUID;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public abstract class IdUtils {
    public static String uuid() {
        return uuid(32);
    }

    public static String uuid(int length) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }
}
