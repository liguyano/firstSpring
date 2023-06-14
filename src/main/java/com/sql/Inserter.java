package com.sql;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class Inserter {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final Logger logger = LogManager.getLogger(Inserter.class);
    static final String DB_URL = "jdbc:mysql://43.136.94.231:3306/dia?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "user01";
    static final String PASS = "WWPdsg12";
    private Connection conn = null;
    private Statement stmt = null;
    private String rightpassw=null;
    private boolean stmt_closed=true;
    public void close()
    {
        try {
            stmt.close();
            conn.close();
            stmt_closed=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void closeQueue()
    {    try {
        if (stmt_closed)
        {}
        else
        {
        stmt.close();
        stmt_closed=true;
        }
    } catch (SQLException e) {

    }
    }
    public Inserter() {
        try {
             Class.forName(JDBC_DRIVER);
//            System.setProperty("jdbc.drivers",JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public void insert(String sql)
    {
        /**
         * When don't need the return value ,use this.
         * */
        try {
            if (stmt_closed)
            {
                stmt = conn.createStatement();
                stmt_closed=false;
            }
            stmt.execute(sql);
        } catch (SQLException e) {
            this.close();
            logger.error("run" +sql +"failed");
            logger.error(e);
            logger.info("try reconnected");
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> select(String sql,String ... col )
    {
        /**
         * @brief If you need return use, this.
         * @arg sql is the sql statment , the col is a list that
         * contains the name of your return value.
         * */
        ArrayList<String> result=new ArrayList<>();
        try {
            if (stmt_closed)
            {
                stmt = conn.createStatement();
                stmt_closed=false;
            }
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next())
            {
                for (String s: col
                     ) {
                    result.add(rs.getString(s));
                }
            }
            return result;
        } catch (SQLException e) {
            this.close();
            logger.error("run" +sql +"failed");
            logger.error(e);
            logger.info("try reconnected");
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }


    }
}