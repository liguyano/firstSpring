package com.sql;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;
@Controller
@Primary
@Qualifier("fileSql")
public class FileSql extends Sql_Father {
    public FileSql()
    {
        System.out.println("file init");
    }
    @Value("${filePath}")
    private String filePath;
    public void add_File(String fileName , long size,int userid,int dir)
    {
        inserter.insert("insert into file (FILENAME,size,USERID,UPLOADTIME,dir) values(\"" +fileName+
                "\"," +size+
                "," +userid+
                ",NOW(),"+
                dir+
                ");");
    }
    public  int createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        System.out.println(folderPath);
        // 检查文件夹是否存在
        if (!folder.exists()) {
            // 文件夹不存在，创建文件夹
            boolean success = folder.mkdirs();
            if (success) {
                System.out.println("文件夹已创建成功！");
                return 1;
            } else {

                System.out.println("文件夹创建失败！");
                return 2;
            }
        } else {
            System.out.println("文件夹已经存在。");
            return 3;
        }
    }
    public String getIdByName(String fileName)
    {
        String sql="SELECT id FROM file WHERE FILENAME=\"%s\" ;";
        sql=String.format(sql,fileName);
        return inserter.select(sql,"id").get(0);
    }
    public String checkName(String fileName ,int dir)
    {
        ArrayList<String> rs;
        String sql ="select * from file where FILENAME='%s' AND dir='%d';";
        sql=String.format(sql,fileName,dir);
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
    public JSONArray get_fileList( int dir)
    {
        return select_json("select * from file where dir="+dir,"id","FILENAME","UPLOADTIME","USERID","size","dir");
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
    public JSONArray get_dirs(int pre)
    {
        return select_json("select * from Dir where pre= "+pre+";" ,"id","name","pre");
    }
    public String get_dir_by_id(int dir)
    {
        return inserter.select("select name from Dir where id ="+dir,"name").get(0);
    }
    public String get_id_by_dir(String id)
    {
        return inserter.select("select id from Dir where name =\""+id+"\"","id").get(0);
    }
    public void add_dir(String name,int pre)
    {
        inserter.insert("insert into Dir (name,pre) values(\""+
                name+"\","+
                pre+
                ");");
    }
    public void delete_file(int fileId)
    {
        String sql=String.format("DELETE FROM file WHERE id =%d ;",fileId);
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
