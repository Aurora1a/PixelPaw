package com.pixelpaw.common.exception;

import com.pixelpaw.common.api.Result;

import jakarta.servlet.http.HttpServletResponse;
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

    /** 兜底：所有未知异常 + Sa-Token 未登录异常 */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, HttpServletResponse response) {
        // 【精准拦截】用全限定类名判断，避免 pw-common 依赖 sa-token
        if ("cn.dev33.satoken.exception.NotLoginException".equals(e.getClass().getName())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 强制 HTTP 401
            return Result.fail(401, "未登录或登录已过期");
        }

        log.error("Unexpected error", e);
        return Result.fail(500, "服务器内部错误");
    }
}
