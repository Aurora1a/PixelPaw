package com.pixelpaw.pet.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pixelpaw.common.api.Result;
import com.pixelpaw.common.exception.BizException;
import com.pixelpaw.pet.entity.Pet;
import com.pixelpaw.pet.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.dev33.satoken.stp.StpUtil;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@Validated
public class PetController {

    private final PetService petService;

    /**
     * GET /api/pets?page=1&size=10
     * 分页查询宠物列表（自动过滤 deleted=1）
     */
    @GetMapping
    public Result<Page<Pet>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        return Result.ok(petService.page(new Page<>(page, size)));
    }

    /**
     * GET /api/pets/{id}
     * 根据 ID 获取单个宠物详情
     */
    @GetMapping("/{id}")
    public Result<Pet> getById(@PathVariable Long id) {
        Pet pet = petService.getById(id);
        if (pet == null) {
            return Result.fail(404, "宠物不存在");
        }
        return Result.ok(pet);
    }

    /**
     * POST /api/pets
     * 新增宠物（name 非空校验）
     */
    @PostMapping
    public Result<Long> add(@RequestBody @Valid PetAddRequest req) {
        Pet pet = new Pet();
        // TODO Step 5：登录做好后，从 Sa-Token 取当前登录用户 id 替换这个占位
        pet.setUserId(StpUtil.getLoginIdAsLong());
        pet.setName(req.getName());
        pet.setSpecies(req.getSpecies());
        pet.setLevel(req.getLevel() != null ? req.getLevel() : 1);
        pet.setHunger(req.getHunger() != null ? req.getHunger() : 100);
        pet.setMood(req.getMood() != null ? req.getMood() : 100);
        pet.setEnergy(req.getEnergy() != null ? req.getEnergy() : 50);
        petService.save(pet); // 触发 AutoFillHandler → createdAt/updatedAt/deleted 自动填充
        return Result.ok(pet.getId());
    }

    /**
     * PUT /api/pets/{id}
     * 更新宠物信息
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Valid Pet pet) {
        Pet existing = petService.getById(id);
        if (existing == null) {
            throw new BizException(404, "宠物不存在");
        }
        pet.setId(id);
        petService.updateById(pet);
        return Result.ok(null);
    }

    /**
     * DELETE /api/pets/{id}
     * 逻辑删除（deleted 列置为 1，不真删数据）
     */
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        boolean success = petService.removeById(id);
        if (!success) {
            throw new BizException(404, "宠物不存在或已删除");
        }
        return Result.ok(null);
    }

    /**
     * 新增宠物请求 DTO —— 避免直接暴露实体字段
     */
    public static class PetAddRequest {
        @NotBlank(message = "宠物名不能为空")
        private String name;
        private String species = "SLIME";
        private Integer level;
        private Integer hunger;
        private Integer mood;
        private Integer energy;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSpecies() { return species; }
        public void setSpecies(String species) { this.species = species; }
        public Integer getLevel() { return level; }
        public void setLevel(Integer level) { this.level = level; }
        public Integer getHunger() { return hunger; }
        public void setHunger(Integer hunger) { this.hunger = hunger; }
        public Integer getMood() { return mood; }
        public void setMood(Integer mood) { this.mood = mood; }
        public Integer getEnergy() { return energy; }
        public void setEnergy(Integer energy) { this.energy = energy; }
    }
}
