package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /*
        添加购物车
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /*
        查询购物车
     */
    List<ShoppingCart> showShoppingCart();

    /*
        清空购物车
     */
    void cleanShoppingCart();

    /*
        删除购物车中的一个商品
     */
    void deleteOne(ShoppingCartDTO shoppingCartDTO);
}
