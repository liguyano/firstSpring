package com.example.springtry2;

import com.sql.FileSql;

public class DuplicateTest {
    public static void main(String[] args) {
        FileSql fileSql=new FileSql();
        System.out.printf("is %b\n",fileSql.isDuplicate("hello",0));//true
        System.out.printf("is %b\n",fileSql.isDuplicate("rainbow",0));//false
        fileSql.close();
    }
}
