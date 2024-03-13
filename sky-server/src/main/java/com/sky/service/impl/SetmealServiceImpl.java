package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;
    /*
        新增套餐
     */
    @Override
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //添加套餐信息
        setmealMapper.addSetmeal(setmeal);
        //添加套餐菜品关系
        List<SetmealDish> list = setmealDTO.getSetmealDishes();
        list.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertBatch(list);
    }

    /*
        套餐分页查询
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.page(setmealPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }

    /*
        根据id批量删除套餐
     */
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        //查询是否有套餐正在售卖
        List<Long> list = setmealMapper.getByIdsWithStatus(ids);
        if(list != null && list.size() > 0)
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        //批量删除套餐
        setmealMapper.deleteByIds(ids);
        //批量删除套餐菜品关系
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /*
        根据id查询套餐信息
     */
    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        //获取套餐信息
        Setmeal setmeal = setmealMapper.getById(id);
        //获取套餐和菜品的关系
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /*
        更新套餐信息
     */
    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        //删除已有的套餐菜品关系
        List<Long> setmealIds = new ArrayList<>();
        setmealIds.add(setmealDTO.getId());
        setmealDishMapper.deleteBySetmealIds(setmealIds);
        //更新套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        //插入新的套餐菜品关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /*
        起售或停售套餐
     */
    @Override
    public void starOrStop(Integer status, Long id) {
        //判断套餐起售时是否包含未起售商品
        if(status == StatusConstant.ENABLE) {
            List<Long> dishIds = setmealDishMapper.getDishIdsBySetmealId(id);
            List<Long> unDishIds = setmealDishMapper.getDisableDishIdByDishIdsWithStatus(dishIds);
            if(unDishIds != null && unDishIds.size() > 0)
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        }
        //起售、停售套餐
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(setmeal);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
