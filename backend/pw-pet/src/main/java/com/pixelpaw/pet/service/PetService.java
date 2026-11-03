package com.pixelpaw.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pixelpaw.pet.entity.Pet;

public interface PetService extends IService<Pet> {
    // IService 已提供几乎所有常用方法：
    //   save(entity)           → INSERT（触发 AutoFillHandler）
    //   page(page, queryWrapper) → 物理分页 SELECT ... LIMIT offset, size
    //   removeById(id)         → DELETE（触发 @TableLogic → updated deleted=1）
    //   updateById(entity)     → UPDATE SET ... WHERE id = ?
    //   getById(id)            → SELECT ... WHERE id = ? AND deleted = 0
}
