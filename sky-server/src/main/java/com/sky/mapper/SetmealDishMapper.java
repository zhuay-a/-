package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    /*
        根据菜品id查询套餐id
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void deleteBySetmealIds(List<Long> setmealIds);


    void insertBatch(List<SetmealDish> list);

    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);

    @Select("select dish_id from setmeal_dish where setmeal_id = #{setmealId}")
    List<Long> getDishIdsBySetmealId(Long setmealId);

    List<Long> getDisableDishIdByDishIdsWithStatus(List<Long> dishIds);
}
