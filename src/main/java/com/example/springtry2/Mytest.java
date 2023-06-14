package com.example.springtry2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Mytest {
    private int times=0;
    public Mytest()
    {
        times=20;
    }
@GetMapping("/hello")
public String hello()
{
    return "hello"+times++;
}
@GetMapping("/kon")
public String konitiha()
{
return "こんにちは！";
}
}
