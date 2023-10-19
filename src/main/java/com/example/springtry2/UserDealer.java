package com.example.springtry2;

import com.User.AccountSql;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UserDealer {
private AccountSql accountSql;
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

@PreDestroy
    public void onClose()
{
    accountSql.close();
}

}
