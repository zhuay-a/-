package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

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
}
