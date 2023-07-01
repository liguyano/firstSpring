package com.example.springtry2;

import com.sql.FileSql;
import jakarta.annotation.PreDestroy;
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
    private String filePath = "/home/ubuntu/apache-tomcat-10.1.10/webapps/spring/outFile/";

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
   , @RequestParam("path") String path) {
        String currentDir = System.getProperty("user.dir");
        log.info(path);
        log.info(dir);
        log.info("当前目录是：" + currentDir);
        log.info("file Come");
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        fileName = fileSql.checkName(fileName);
        try {
            fileSql.add_File(path+fileName, file.getSize(), (int) request.getSession().getAttribute("user"),dir);
            fileName = fileSql.getIdByName(path+fileName);
            log.info(path+fileName);
            File dest = new File(filePath +path+"/"+ fileName);
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
    ,@RequestParam("path") String path
    ) {
        int re=fileSql.createFolderIfNotExists(filePath +path+ dir);
        if (re==1)
        {
            fileSql.add_dir(dir,pre);
        }
        return re;
    }

    @GetMapping("dir")
    public String get_dir(@RequestParam("pre") int p){
        return fileSql.get_dirs(p).toString();
    }
}


