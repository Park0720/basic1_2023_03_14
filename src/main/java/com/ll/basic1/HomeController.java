package com.ll.basic1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/home/main")
    @ResponseBody
    public String showMain(){
        return "안녕하세요!!!";
    }
    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2(){
        return "반갑습니다.";
    }
    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3(){
        return "즐거웠습니다.";
    }
    private int response;
    private int call;
    public HomeController(){
        this.response = 0;
        this.call = 1;
    }
    @GetMapping("/home/increase")
    @ResponseBody

    public String showincrease(){
        return "요청횟수 : " + call++ + "\n" +"응답횟수 : " + response++;
    }
}
