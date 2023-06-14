package com.example.springtry2;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sql.User;
import sql.UserLoginMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@SpringBootTest
public class SqlTest {
    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //template模板，拿来即用
        connection.close();
    }

    @Autowired
    UserLoginMapper userLoginMapper  ;

    @Test
    public void test()
    {
        List<User> u= userLoginMapper.queryAll();
        System.out.println(u.size());
    }

}
