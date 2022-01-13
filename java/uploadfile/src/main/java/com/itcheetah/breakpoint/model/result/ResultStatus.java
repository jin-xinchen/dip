package com.itcheetah.breakpoint.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultStatus implements IResultCode {

    /**
     * 系统未知异常
     */
    FAIL(ResultStatus.DEFAULT_FAILURE_CODE, "请求数据提交失败"),

    /**
     * 操作成功
     */
    SUCCESS(ResultStatus.DEFAULT_SUCCESS_CODE, "请求数据提交成功");

    /**
     * 默认失败的编码
     */
    public static final int DEFAULT_FAILURE_CODE = 10000;

    /**
     * 默认成功编码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;



}

