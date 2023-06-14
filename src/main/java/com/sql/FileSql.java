package com.sql;

import com.alibaba.fastjson.JSONArray;
import com.sql.Sql_Father;

import java.io.File;
import java.util.ArrayList;

public class FileSql extends Sql_Father {
    public void add_File(String fileName , long size,int userid)
    {
        inserter.insert("insert into file (FILENAME,size,USERID,UPLOADTIME) values(\"" +fileName+
                "\"," +size+
                "," +userid+
                ",NOW());");
    }
    public String getIdByName(String fileName)
    {
        String sql="SELECT id FROM file WHERE FILENAME=\"%s\" ;";
        sql=String.format(sql,fileName);
        return inserter.select(sql,"id").get(0);
    }
    public String checkName(String fileName)
    {
        ArrayList<String> rs;
        String sql ="select * from file where FILENAME='%s'";
        sql=String.format(sql,fileName);
        String nf=fileName;
        rs=inserter.select(sql,"id");
        int i=0;
        while (true)
        {
            if (rs.size()<1)
            {
                break;
            }else
            {
                nf=String.format("(%d)%s",++i,fileName);
                 sql ="select * from file where FILENAME='%s'";
                sql=String.format(sql,nf);
                rs=inserter.select(sql,"id");
            }
        }
        return nf;
    }
    public JSONArray get_fileList()
    {
        return select_json("select * from file","id","FILENAME","UPLOADTIME","USERID","size");
    }
    public ArrayList<File> getAllFile(String dir ) {
        ArrayList<File> allFileList=new ArrayList<>();
        // 获取文件列表
        File fileInput =new File(dir);
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                // 递归处理文件夹
                // 如果不想统计子文件夹则可以将下一行注释掉
                //getAllFile(file, allFileList);
            } else {
                // 如果是文件则将其加入到文件数组中
                System.out.println(file.getName());
                allFileList.add(file);
            }
        }
        return allFileList;
    }
}
