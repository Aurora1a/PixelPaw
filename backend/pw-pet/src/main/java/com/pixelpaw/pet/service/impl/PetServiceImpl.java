package com.pixelpaw.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pixelpaw.pet.entity.Pet;
import com.pixelpaw.pet.mapper.PetMapper;
import com.pixelpaw.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /** Redis Key 前缀：pet:1, pet:2, ... */
    private static final String CACHE_KEY_PREFIX = "pw:pet:";

   /**
     * 带缓存的查询（覆盖父类 getById）
     */
    // @Override  // 删掉这行
    public Pet getById(Long id) {
        String cacheKey = CACHE_KEY_PREFIX + id;
        
        // 1. 查 Redis
        Pet pet = (Pet) redisTemplate.opsForValue().get(cacheKey);
        if (pet != null) {
            return pet; // 缓存命中，直接返回（控制台不打 SQL）
        }
        
        // 2. 查数据库
        pet = super.getById(id);
        if (pet != null) {
            // 3. 写入 Redis，1 小时过期
            redisTemplate.opsForValue().set(cacheKey, pet, 1, TimeUnit.HOURS);
        }
        return pet;
    }

    /**
     * 新增宠物 → 删缓存（防止脏数据）
     */
    @Override
    public boolean save(Pet entity) {
        boolean result = super.save(entity);
        if (result && entity.getId() != null) {
            String cacheKey = CACHE_KEY_PREFIX + entity.getId();
            redisTemplate.delete(cacheKey);
        }
        return result;
    }

    /**
     * 更新宠物 → 删缓存
     */
    @Override
    public boolean updateById(Pet entity) {
        boolean result = super.updateById(entity);
        if (result && entity.getId() != null) {
            String cacheKey = CACHE_KEY_PREFIX + entity.getId();
            redisTemplate.delete(cacheKey);
        }
        return result;
    }

    
}