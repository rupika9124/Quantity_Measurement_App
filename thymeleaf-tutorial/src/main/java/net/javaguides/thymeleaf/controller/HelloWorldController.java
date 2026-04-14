package net.javaguides.thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    //handler method to handle /helloworld request
    // http:localhost:8080/hello-world
    @GetMapping("/hello-world")
    public String helloWorld(Model model){ // we are using the Model interface to store the model data
        model.addAttribute("message","hello World!");
        return "hello-world"; // we are returning the view
    }

}
