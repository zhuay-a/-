package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /*
        动态条件查询
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /*
        修改商品数量
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /*
        删除用户购物车商品
     */
    void delete(ShoppingCart shoppingCart);
}
