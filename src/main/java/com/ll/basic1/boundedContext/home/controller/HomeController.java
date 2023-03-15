package com.ll.basic1.boundedContext.home.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class HomeController {
    private List<Person> people;
    private int response;
    private int call;

    public HomeController() {
        this.response = 0;
        this.call = 1;
        people = new ArrayList<>();
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain() {
        return "안녕하세요!!!";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody

    public String showIncrease() {
        return "요청횟수 : " + call++ + " "
                + "응답횟수 : " + response++;
    }

    @GetMapping("/home/plus")
    @ResponseBody

    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody

    public String AddPerson(String name, int age) {
        Person person = new Person(name, age);
        people.add(person);
        return "%d번 사람이 추가되었습니다.".formatted(person.getId());
    }

    @GetMapping("/home/removePerson")
    @ResponseBody

    public String removePerson(int id) {
        boolean removed = people.removeIf(person -> person.getId() == id);
        /* 스트림 사용 안하고 하는 방법
        for ( Person p : people ) {
             if ( p.getId() == id ) people.remove(p);
        } */
        if (removed == false) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        return "%d번 사람이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody

    public String modifyPerson(int id, String name, int age) {
        Person found = people
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        found.setName(name);
        found.setAge(age);

        return "%d번 사람이 수정되었습니다.".formatted(id);

        /* 내가 만든 코드
        boolean removed = people.removeIf(person -> person.getId() == id);
        if (removed == false){
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        people.add(id-1,new Person(name,age));
        return "%d번 사람이 수정되었습니다.".formatted(id);
         */
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }

    @GetMapping("/home/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        resp.getWriter().append("Hello, you are %d years old.".formatted(age));
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException { // 리턴되는 int 값은 String 화 되어서 고객(브라우저)에게 전달된다.
        int countInCookie = 0;

        // 고객이 가져온 쿠폰에서 count 쿠폰을 찾고 그 쿠폰의 값을 가져온다.
        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        int newCountInCookie = countInCookie + 1;

        // 고객이 가져온 count 쿠폰값에 1을 더한 쿠폰을 만들어서 고객에게 보낸다.
        // 쉽게 말하면 브라우저(고객)에 저장되어 있는 count 쿠폰의 값을 1 증가시킨다.
        // 이렇게 브라우저의 쿠키값을 변경하면 재방문시에 스프링부트가 다시 그 값을 받게 되어 있다.
        resp.addCookie(new Cookie("count", newCountInCookie + ""));

        // 응답 본문
        return newCountInCookie;
    }

}

@AllArgsConstructor
@Getter
@ToString
class Person {
    private static int lastId;
    private int id;
    @Setter
    private String name;
    @Setter
    private int age;

    static {
        lastId = 0;
    }

    Person(String name, int age) {
        this(++lastId, name, age);
    }
}