package com.github.taccisum.excp;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 通用业务异常
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-08-10
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseBizException extends RuntimeException {
    @Getter
    private ErrorCode code;

    public BaseBizException() {
        this(CommonErrorCode.DEFAULT_BIZ_ERROR);
    }

    public BaseBizException(ErrorCode code) {
        this.code = code;
    }

    public BaseBizException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public BaseBizException(String message) {
        this(CommonErrorCode.DEFAULT_BIZ_ERROR, message);
    }

    public BaseBizException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseBizException(String message, Throwable cause) {
        this(CommonErrorCode.DEFAULT_BIZ_ERROR, message, cause);
    }
}
