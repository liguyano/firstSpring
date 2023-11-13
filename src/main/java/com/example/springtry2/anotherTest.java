package com.example.springtry2;

import com.sql.FileSql;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Component
@RequestMapping("")
public class anotherTest {
    public static final Logger log = LogManager.getLogger(anotherTest.class);
    @Autowired
    private final FileSql fileSql;
    @Value("${filePath}")
    private String filePath =" E:/apache-tomcat-10.1.10/webapps/spring/outFile";

    public anotherTest(FileSql fileSql) {
        log.info("file path" + filePath);
        fileSql.createFolderIfNotExists(filePath);
        this.fileSql = fileSql;
    }

    @GetMapping("ana")
    public String ana() {
        return "안녕하세요";
    }

    @PostMapping("uploadfile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @RequestParam("upload") MultipartFile file,@RequestParam("dir" )int dir
   ) {
        String currentDir = System.getProperty("user.dir");
        log.info(dir);
        log.debug("当前目录是：" + currentDir);
        log.info("file Come");
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        fileName = fileSql.checkName(fileName,dir);
        int userid=0;
        Cookie[]cookies=request.getCookies();
        String cookieValue=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    cookieValue = cookie.getValue();
                    userid=Integer.parseInt(cookieValue);
                }
            }
        }
        try {
            fileSql.add_File(fileName, file.getSize(), userid,dir);
            fileName = fileSql.getIdByName(fileName);
            File dest = new File(filePath +"/"+ fileName);
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            log.error(e);
        }
        return "上传失败！";
    }

    @GetMapping("get-file")
    public String getFileLilt(@RequestParam("dir") int dir) {
        return fileSql.get_fileList(dir).toString();
    }

    @PreDestroy
    public void onClose() {
        log.info("close");
        fileSql.close();
    }
    @PostMapping("add-dir")
    public int add_file(@RequestParam("dir-name") String dir,@RequestParam("pre") int pre

    ) {
        fileSql.add_dir(dir,pre);
        return 1;
    }

    @GetMapping("dir")
    public String get_dir(@RequestParam("pre") int p){
        return fileSql.get_dirs(p).toString();
    }

    @PostMapping("del-file")
    public String del_file(@RequestParam("id") int fileId,@RequestParam("password") String password)
    {
        if (fileSql.checkPass("root",password)>0)
        {

        }else
        {
            return "password wrong";
        }
        fileSql.delete_file(fileId);
        if (deleteFile(filePath+"/"+String.valueOf(fileId)))
        {
            log.info("ok");
            return "ok";
        }
        log.info("file");
        return "fail";
    }
    public  boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // Check if file exists before deleting it.
        if (file.exists()) {
            return file.delete();
        } else {
            log.info(filePath + "not exit");
            return false;
        }
    }
}


