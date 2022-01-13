package com.itcheetah.breakpoint.exception;

import lombok.Getter;

/**
 * @className: JsonException
 * @description: TODO
 * @author: zyq
 * @date: 2021/7/28 11:32
 * @Version: 1.0
 */
@Getter
public class JsonException extends BaseException {

    /**
     *
     */
    private static final long serialVersionUID = 1411898061046062846L;

    /**
     * JsonException
     * @param message
     */
    public JsonException(String message) {
        super(-1, message);
    }

    /**
     * JsonException
     * @param code
     * @param message
     */
    public JsonException(Integer code, String message) {
        super(code, message);
    }

    /**
     * JsonException
     * @param resultEnum
     */
    public JsonException(IErrorCode resultEnum) {
        super(resultEnum);
    }

    /**
     * JsonException
     * @param message
     * @param cause
     */
    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
