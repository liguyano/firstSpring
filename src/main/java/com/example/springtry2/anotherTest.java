package com.example.springtry2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class anotherTest {
    @GetMapping("ana")
    public String ana()
    {
        return "안녕하세요";
    }
}
