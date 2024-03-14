package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DIshController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
        新增菜品
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        clearCache("dish_" + dishDTO.getCategoryId());

        return Result.success();
    }
    /*
        菜品分页查询
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dto) {
        log.info("菜品分页查询: {}", dto);
        PageResult pageResult = dishService.pageQuery(dto);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除: {}", ids);
        dishService.deleteBatch(ids);

        clearCache("dish_*");

        return Result.success();
    }

    /*
        根据id查询菜品和对应的口味数据
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和对应的口味数据")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品和对应的口味数据: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /*
        修改菜品信息
     */
    @PutMapping("")
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息: {}", dishDTO);
        dishService.updateDishWithFlavor(dishDTO);

        clearCache("dish_*");

        return Result.success();
    }
    /*
        起售停售菜品
     */
    @PostMapping("/status/{status}")
    @ApiOperation("起售停售菜品")
    public Result startOrStopDish(@PathVariable Integer status, Long id) {
        dishService.starOrStop(id, status);

        clearCache("dish_*");

        return Result.success();
    }

    /*
        根据分类查询菜品
     */
    @GetMapping("/list")
    @ApiOperation("根据分类查询菜品")
    public Result<List<Dish>> getByCategoryId(Long categoryId) {
        List<Dish> dishList = dishService.getByCategoryId(categoryId);
        return Result.success(dishList);
    }

    /*
    清理redis缓存数据
 */
    private void clearCache(String pattern) {
        Set set = redisTemplate.keys(pattern);
        redisTemplate.delete(set);
    }
}
