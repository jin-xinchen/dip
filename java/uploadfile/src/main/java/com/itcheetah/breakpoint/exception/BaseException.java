package com.itcheetah.breakpoint.exception;

import com.itcheetah.breakpoint.model.result.IResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: BaseException
 * @description: TODO
 * @author: zyq
 * @date: 2021/7/28 11:32
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -9070366652885045261L;

    /**
     * code
     */
    private Integer code;

    /**
     * message
     */
    private String message;


    public BaseException(IResultCode resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        doFillInStackTrace();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        doFillInStackTrace();
    }

    public BaseException(String message, Throwable cause) {
        super(message);
        doFillInStackTrace();
    }

    /**
     * 提高性能
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

}
