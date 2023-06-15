package com.example.springtry2;

import com.sql.FileSql;
import jakarta.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("")
public class anotherTest {
    public static final Logger log = LogManager.getLogger(anotherTest.class);
    private FileSql fileSql;
    public anotherTest()
    {
        fileSql=new FileSql();
    }
    @GetMapping("ana")
    public String ana()
    {
        return "안녕하세요";
    }

    @PostMapping("uploadfile")
    @ResponseBody
    public String uploadFile(@RequestParam("upload") MultipartFile file) {
        String currentDir = System.getProperty("user.dir");
        log.info("当前目录是：" + currentDir);
        log.info("file Come");
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\apache-tomcat-10.1.10\\webapps\\spring\\outFile\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            log.error(e);
        }
        return "上传失败！";
    }
    @GetMapping("get-file")
    public String getFileLilt()
    {
        return fileSql.get_fileList().toString();
    }
    @PreDestroy
    public void onClose()
    {
        log.info("close");
        fileSql.close();

    }
}


