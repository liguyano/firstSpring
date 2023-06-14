package com.User;
import com.sql.Sql_Father;

import java.util.ArrayList;

public class AccountSql extends Sql_Father {
    public void submit(String username,String password) {
        String sql = "insert into users(name,password) values(\"%s\",\"%s\")";
        sql=String.format(sql,username,password);
        inserter.insert(sql);
    }

    public  int checkPass(String username,String password) {
        String sql;
//            sql = "insert into user(user,password) values(\"%s\",\"%s\")";
//            sql=String.format(sql,username,password);
        sql= "select * from users where name=\"%s\"";
        sql=String.format(sql,username);
        ArrayList<String> pass=inserter.select(sql,"password","id");
        if (pass.size()<1)
        {
            return -1;//not exeist
        }else
        {
            if (pass.get(0).equals(password))
            {
                return Integer.parseInt(pass.get(1));
            }else
            {
                return -2;
            }
        }
    }
}
