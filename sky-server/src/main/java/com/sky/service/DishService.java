package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {


    /*
            新增菜品和对应的口味数据
         */
    public void saveWithFlavor(DishDTO dishDTO);

    /*
        菜品分页查询
     */
    PageResult pageQuery(DishPageQueryDTO dto);

    /*
        菜品批量删除功能
     */
    void deleteBatch(List<Long> ids);

    /*
        根据id查询菜品和对应的口味数据
     */
    DishVO getByIdWithFlavor(Long id);

    /*
        根据菜品id修改菜品基本信息和口味信息
     */
    void updateDishWithFlavor(DishDTO dishDTO);

    /*
        起售或停售菜品
     */
    void starOrStop(Long id, Integer status);

    List<Dish> getByCategoryId(Long categoryId);
}
