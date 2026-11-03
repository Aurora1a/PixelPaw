package com.pixelpaw.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pixelpaw.pet.entity.Pet;
import com.pixelpaw.pet.mapper.PetMapper;
import com.pixelpaw.pet.service.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {
    // ServiceImpl 把 PetMapper 注入为 this.baseMapper，
    // 所有 IService/BaseMapper 方法都可直接调用。
    // 复杂业务逻辑（如校验、组合操作）在这里扩展。
}
