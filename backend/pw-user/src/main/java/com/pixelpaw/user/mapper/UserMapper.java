package com.pixelpaw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pixelpaw.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 基础的增删改查由 BaseMapper 自动提供：
    //   insert(User)          → INSERT INTO t_user ...
    //   selectById(id)       → SELECT * FROM t_user WHERE id = ? AND deleted = 0
    //   deleteById(id)       → UPDATE t_user SET deleted = 1 WHERE id = ?
    //   updateById(user)      → UPDATE t_user SET ... WHERE id = ?
    //   selectList(query)    → 复杂查询用 QueryWrapper / LambdaQueryWrapper
}
