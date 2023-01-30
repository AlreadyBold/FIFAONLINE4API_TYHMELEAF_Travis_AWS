package com.example.fifatoy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class welcomecontroller {
    
    @GetMapping("/")
    public String index(Model model) {
        //student student = new student();
        //student.setId("2001");
        //student.setName("Anne Marie");
        //student.setAge(29);
        //thymeleaf에서 사용할 object명, object를 ModelAndview에 넣어준다. 
        //model.addAttribute("ss", student);
        return "welcome";
    }





}