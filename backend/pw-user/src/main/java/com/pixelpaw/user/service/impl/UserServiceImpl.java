package com.pixelpaw.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pixelpaw.common.exception.BizException;
import com.pixelpaw.user.entity.User;
import com.pixelpaw.user.mapper.UserMapper;
import com.pixelpaw.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getByPhone(String phone) {
        return lambdaQuery().eq(User::getPhone, phone).one();
    }

    @Override
    public User register(String phone, String rawPassword, String username) {
        if (getByPhone(phone) != null) {
            throw new BizException(400, "手机号已注册");
        }
        User user = new User();
        user.setPhone(phone);
        // 明文密码绝不落库：先 BCrypt 再存
        user.setPassword(passwordEncoder.encode(rawPassword));
        String suffix = phone.length() > 4 ? phone.substring(phone.length() - 4) : phone;
        user.setUsername(StringUtils.hasText(username) ? username : "PixelPaw_" + suffix);
        user.setAvatar("");
        user.setStatus(1);
        save(user);
        return user;
    }

    @Override
    public User login(String phone, String rawPassword) {
        return login(phone, null, rawPassword);
    }

    @Override
    public User login(String phone, String username, String rawPassword) {
        boolean byPhone = StringUtils.hasText(phone);
        if (byPhone == StringUtils.hasText(username)) {
            throw new BizException(400, "请选择一种登录方式");
        }
        java.util.List<User> matches = byPhone
            ? lambdaQuery().eq(User::getPhone, phone.trim()).list()
            : lambdaQuery().eq(User::getUsername, username.trim()).list();
        User user = matches.size() == 1 ? matches.get(0) : null;
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            // 故意不说“手机号还是密码错”，避免撞库枚举
            throw new BizException(400, "账号或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BizException(403, "账号已被封禁");
        }
        return user;
    }
}
