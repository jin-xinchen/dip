package com.itcheetah.securityjwt.exception;

import com.itcheetah.securityjwt.common.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author bee
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public AjaxResult businessException(BusinessException businessException) {
        return AjaxResult.error(businessException.getStatus().getCode()
                , businessException.getStatus().getMessage());
    }

}
