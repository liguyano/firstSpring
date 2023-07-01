package com.example.springtry2;

import com.alibaba.fastjson.JSONArray;
import com.sql.FileSql;

public class getDir {
    public static void main(String[] args) {

        FileSql fileSql=new FileSql();
        JSONArray j=fileSql.get_dirs(0);
        String a=fileSql.get_id_by_dir("ffdd");
        System.out.println(a);
        System.out.println(j.toString());
    }
}
