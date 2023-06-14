package com.sql;

import sql.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserLoginMapper {

    public List<User> queryAll();
    public int add(User userLogin);
    //根据用户名查询数据
    public User queryByName(String username);
}
