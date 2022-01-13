package com.itcheetah.breakpoint.model.result;

import com.itcheetah.breakpoint.exception.IErrorCode;
import com.itcheetah.breakpoint.exception.JsonException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;


/**
 * @className: Result
 * @description: 返回类
 * @author: itcheetah
 * @date: 2021/7/28 11:28
 * @Version: 1.0
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * success
     */
    private boolean success;

    /**
     * T <>
     */
    private T data;

    /**
     * message 返回信息
     */
    private String message;

    /**
     * code 错误码
     */
    private Integer code;

    /**
     * url 请求路径
     */
    private String url;

    /**
     * errorStackTrack 返回错误信息堆栈
     */
    private String errorStackTrack;

    /**
     * 请求结果生成的时间戳
     */
    private Instant time;

    /**
     * 构造函数
     */
    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 实体类
     * @param success
     * @param data
     * @param message
     * @param code
     */
    public Result(boolean success, T data, String message, Integer code) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param resultType
     */
    public Result(IResultCode resultType) {
        this.code = resultType.getCode();
        this.message = resultType.getMessage();
        this.time = ZonedDateTime.now().toInstant();
    }


    public Result(boolean success, String message, Integer code) {
        Result.of(success, null, message, code);
    }

    public Result(boolean success, IResultCode resultType) {
        Result.of(success, null, resultType.getMessage(), resultType.getCode());
    }

    /**
     * @param errorType
     * @param data
     */
    public Result(boolean success, IResultCode resultType, T data) {
        Result.of(success, data, resultType.getMessage(), resultType.getCode());
    }


    /**
     * 构造一个自定义的API返回
     *
     * @param success 是否成功
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     * @return Result
     */
    public static <T> Result<T> of(boolean success, T data, String message, Integer code) {
        return new Result<T>(success, data, message, code);
    }

    /**
     *
     * @Title: success
     * @param @return
     * @return Result<T>
     * @throws
     */
    public static <T> Result<T> ok() {
        return Result.ok(ResultStatus.SUCCESS);
    }

    /**
     *
     * @Title: success
     * @param @param data
     * @param @return
     * @return Result<T>
     * @throws
     */
    public static <T> Result<T> ok(T data) {
        return Result.of(true,data,
                ResultStatus.SUCCESS.getMessage(),ResultStatus.SUCCESS.getCode());
    }

    /**
     * 添加返回信息
     * @param message
     * @return
     */
    public static <T> Result<T> ok(IResultCode resultEnum) {
        return Result.of(true, null, resultEnum.getMessage(),
                resultEnum.getCode());
    }

    /**
     * 添加返回信息
     * @param message
     * @return
     */
    public static <T> Result<T> ok(String message) {
        return Result.of(true, null, message,
                ResultStatus.DEFAULT_SUCCESS_CODE);
    }

    /**
     * 添加返回信息
     * @param message
     * @param code
     * @return
     */
    public static <T> Result<T> ok(String message, Integer code) {
        return Result.of(true, null, message, code);
    }

    /**
     * 添加返回信息
     * @param data
     * @param message
     * @return
     */
    public static <T> Result<T> ok(T data, String message) {
        return Result.of(true, data, message,
                ResultStatus.DEFAULT_SUCCESS_CODE);
    }

    /**
     *
     * @Title: 添加返回信息
     * @param data
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> Result<T> fail(T data) {
        return Result.of(false, data, ResultStatus.FAIL.getMessage(), ResultStatus.FAIL.getCode());
    }

    /**
     * @Title: failure
     * @param @return
     * @param message
     * @return
     */
    public static <T> Result<T> fail(IResultCode resultCode) {
        return Result.of(false, null, resultCode.getMessage(), resultCode.getCode());
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return Result
     */
    public static <T> Result<T> fail(IResultCode resultCode, T data) {
        return Result.of(false, data, resultCode.getMessage(), resultCode.getCode());
    }

    /**
     * @Title: failure
     * @param @return
     * @param message
     * @return
     */
    public static <T> Result<T> fail(String message) {
        return Result.of(false, null, message, ResultStatus.DEFAULT_FAILURE_CODE);
    }


    /**
     *
     * @param message
     * @param errorcode
     * @return
     */
    public static <T> Result<T> fail(String message, Integer errorcode) {
        return  Result.of(false,null,message,errorcode);
    }

    /**
     *
     * @Title: failure
     * @param @return
     * @return Result<T>
     * @throws
     */
    public static <T> Result<T> fail() {
        return Result.fail(ResultStatus.FAIL);
    }

    /**
     *
     * @param errorcode
     * @param message
     * @param data
     * @return
     */
    public static <T> Result<T> fail(T data, String message, Integer errorcode) {
        return Result.of(false,data,message,errorcode);
    }

    /**
     * 直接抛出失败异常，抛出 code 码
     * @param resultCode ResultCode
     */
    public static void throwFail(IErrorCode errorCode) {
        throw new JsonException(errorCode);
    }

    /**
     * 直接抛出失败异常，抛出 code 码
     * @param resultCode ResultCode
     */
    public static void throwFail(String message) {
        throw new JsonException(message);
    }

    /**
     * 直接抛出失败异常，抛出 code 码
     * @param resultCode ResultCode
     */
    public static void throwFail(Integer code, String message) {
        throw new JsonException(message);
    }

    /**
     * 构造一个异常的API返回
     *
     * @param <T> {@link BaseException} 的子类
     * @return Result
     */
    public static <T extends JsonException> Result<T> ofException(T t) {
        return of(false, null, t.getMessage(), t.getCode());
    }

    /**
     * 构造一个异常的API返回
     *
     * @param <T> {@link BaseException} 的子类
     * @param t   编码
     * @return Result
     */
    public static <T extends JsonException> Result<T> ofException(T t, Integer code) {
        return of(false, null, t.getMessage(), code);
    }

}
