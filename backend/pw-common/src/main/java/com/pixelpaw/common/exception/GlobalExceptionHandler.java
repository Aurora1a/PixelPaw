package com.pixelpaw.common.exception;

import com.pixelpaw.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 捕获自定义业务异常 */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        log.warn("BizException: {}", e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /** 捕获参数校验失败（@Valid / @NotBlank 等） */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<Void> handleValidation(Exception e) {
        log.warn("Validation failed: {}", e.getMessage());
        return Result.fail(400, "参数校验失败");
    }

    /** 兜底：所有未知异常 */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("Unexpected error", e);
        return Result.fail(500, "服务器内部错误");
    }
}
