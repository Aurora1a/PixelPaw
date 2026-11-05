package com.pixelpaw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pixelpaw.user.entity.User;

public interface UserService extends IService<User> {
    User getByPhone(String phone);
    User register(String phone, String rawPassword, String username);
    User login(String phone, String rawPassword);
    User login(String phone, String username, String rawPassword);
}
