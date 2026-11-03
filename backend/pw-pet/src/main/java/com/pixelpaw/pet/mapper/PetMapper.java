package com.pixelpaw.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pixelpaw.pet.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PetMapper extends BaseMapper<Pet> {
    // 基础的增删改查由 BaseMapper 自动提供：
    //   insert(Pet)          → INSERT INTO t_pet ...
    //   selectById(id)       → SELECT * FROM t_pet WHERE id = ? AND deleted = 0
    //   deleteById(id)       → UPDATE t_pet SET deleted = 1 WHERE id = ?
    //   updateById(pet)      → UPDATE t_pet SET ... WHERE id = ?
    //   selectList(query)    → 复杂查询用 QueryWrapper / LambdaQueryWrapper
}
