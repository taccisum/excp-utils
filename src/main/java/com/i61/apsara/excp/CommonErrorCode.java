package com.i61.apsara.excp;

import lombok.Getter;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
public enum CommonErrorCode implements ErrorCode {
    /**
     * 无错误
     */
    SUCCESS("1"),
    /**
     * 系统错误
     */
    SYS_ERROR("0"),
    /**
     * 默认业务异常码
     */
    DEFAULT_BIZ_ERROR("2");

    @Getter
    private String code;

    CommonErrorCode(String code) {
        this.code = code;
    }
}
