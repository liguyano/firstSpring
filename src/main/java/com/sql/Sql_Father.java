package com.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class Sql_Father {
    protected Inserter inserter;

    public Sql_Father() {
        this.inserter = new Inserter();
    }
    public void close()
    {
        System.out.println("closed");
        inserter.close();
    }
    public JSONArray select_json(String sql, String ... col)
    {
        ArrayList<String> re=inserter.select(sql,col);
        JSONArray ja=new JSONArray();
        int sizeCol=col.length;
        for (int i =0;i<re.size();i+=sizeCol)
        {
            JSONObject jo=new JSONObject();
            for (int j=0;j<sizeCol;j++)
            {
                jo.put(col[j],re.get(i+j) );
            }
            ja.add(jo);
        }
        return ja;
    }
}
