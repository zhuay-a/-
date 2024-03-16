package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /*
        根据openid查询用户
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenId(String openid);

    /*
        查询用户数据
     */
    void insert(User user);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

}
