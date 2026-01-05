package com.example.springtry2;

import com.User.AccountSql;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("")
public class UserDealer {
private AccountSql accountSql;
private JSONArray messages=new JSONArray();
public UserDealer()
{
    accountSql=new AccountSql();
}
@PostMapping("checkp")
public int checkPassWord(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password)
{
    int a=accountSql.checkPass(username,password);
    System.out.printf("pass return:%d",a);

    if (a>0)
    {
        Cookie pass=new Cookie("user",String.valueOf(a));
        request.getSession().setAttribute("user",a);
        response.addCookie(pass);
    }
    return a;
}
@PostMapping("text3")
public String register(@RequestParam("username") String username,@RequestParam("password") String password)
{
 accountSql.submit(username,password);
 return "OK";
}
@PostMapping("message")
public String recvMessage(@RequestParam("id") String id,@RequestParam("message") String message)
{
    JSONObject jo=new JSONObject();
    jo.put(id,message);
    messages.add(jo);
    return "1";
}
@GetMapping("message")
public String getMessage()
{
    return messages.toString();
}

@PreDestroy
    public void onClose()
{
    accountSql.close();
}

}
