package com.ll.basic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private List<Person> people;
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
        people = new ArrayList<>();
    }
    @GetMapping("/home/increase")
    @ResponseBody

    public String showIncrease(){
        return "요청횟수 : " + call++ + " "
                +"응답횟수 : " + response++;
    }
    @GetMapping("/home/plus")
    @ResponseBody

    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b){
        return a + b;
    }
    @GetMapping("/home/addPerson")
    @ResponseBody

    public String AddPerson(String name, int age){
        Person person = new Person(name, age);
        people.add(person);
        return "%d번 사람이 추가되었습니다.".formatted(person.getId());
    }

    @GetMapping("/home/removePerson")
    @ResponseBody

    public String removePerson(int id){
        boolean removed = people.removeIf(person -> person.getId() == id);
        /* 스트림 사용 안하고 하는 방법
        for ( Person p : people ) {
             if ( p.getId() == id ) people.remove(p);
        } */
        if (removed == false){
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        return "%d번 사람이 삭제되었습니다.".formatted(id);
    }
    @GetMapping("/home/modifyPerson")
    @ResponseBody

    public String modifyPerson(int id, String name, int age){
        Person found = people
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null){
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
    public List<Person> showPeople(){
        return people;
    }
}
@AllArgsConstructor
@Getter
@ToString
class Person{
    private static int lastId;
    private int id;
    @Setter
    private String name;
    @Setter
    private int age;

    static {
        lastId = 0;
    }
    Person(String name, int age){
        this(++lastId, name, age);
    }

}
