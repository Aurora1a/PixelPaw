package com.pixelpaw.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.pixelpaw.common.api.Result;
import com.pixelpaw.user.entity.User;
import com.pixelpaw.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;

    /** 注册：放行，先用来建 admin。只建号，不自动登录。 */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody @Valid RegisterRequest req) {
        User user = userService.register(req.getPhone(), req.getPassword(), req.getUsername());
        return Result.ok(user.getId());
    }

    /** 登录：校验通过 → StpUtil.login(id) 签发 token → 返回 token 字符串 */
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid LoginRequest req) {
        User user = userService.login(req.getPhone(), req.getPassword());
        StpUtil.login(user.getId());
        return Result.ok(StpUtil.getTokenValue());
    }

    /** 登出：受保护接口，必须带合法 token 才能调 */
    @PostMapping("/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.ok();
    }

    public static class RegisterRequest {
        @NotBlank(message = "手机号不能为空")
        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
        private String phone;
        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 32, message = "密码长度 6-32 位")
        private String password;
        private String username;

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }

    public static class LoginRequest {
        @NotBlank(message = "手机号不能为空")
        private String phone;
        @NotBlank(message = "密码不能为空")
        private String password;

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}