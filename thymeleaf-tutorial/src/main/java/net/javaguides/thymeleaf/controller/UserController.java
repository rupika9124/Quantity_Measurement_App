package net.javaguides.thymeleaf.controller;

import net.javaguides.thymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // handler method to handle variable-expression request
    @GetMapping("/variable-expression")
    public String variableExpression(Model model){
        User user = new User("Ramesh","ramesh@gmail.com","ADMIN","Male");
        model.addAttribute("user",user);
        return "variable-expression";
    }

    // handler method to handle selection expression
    // http://localhost:8080/selection-expression
    @GetMapping("selection-expression")
    public String selectionExpression(Model model){
        User user = new User("Ramesh","ramesh@gmail.com","ADMIN","Male");
        model.addAttribute("user",user);
        return "selection-expression";
    }

    // handler method to handle message expressions request
    // http://localhost:8080/message-expression
    @GetMapping("message-expression")
    public String messageExpression(){
        return "message-expression";
    }

    // handler method to handle link expressions
    @GetMapping("/link-expression")
    public String linkExpression(Model model){
        model.addAttribute("id",1);
        return "link-expression";
    }

    // handler method to handle fragment expression request
    @GetMapping("/fragment-expression")
    public String fragmentExpression(){
        return "fragment-expression";
    }

    @GetMapping("/users")
    public String users(Model model){
        User admin = new User("Admin","admin@gmail.com","ADMIN","Male");
        User ramesh = new User("Ramesh","ramesh@gmail.com","USER","Male");
        User meena = new User("Meena","meena@gmail.com","USER","Female");
        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(ramesh);
        users.add(meena);
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping("/if-unless")
    public String ifUnless(Model model){
        User admin = new User("Admin","admin@gmail.com","ADMIN","Male");
        User ramesh = new User("Ramesh","ramesh@gmail.com","USER","Male");
        User meena = new User("Meena","meena@gmail.com","USER","Female");
        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(ramesh);
        users.add(meena);
        model.addAttribute("users",users);
        return "if-unless";
    }

    @GetMapping("/switch-case")
    public String switchCase(Model model){
        User user = new User(
                "Ramesh",
                "ramesh@gmail.com",
                "GUEST",
                "Male"
        );
        model.addAttribute("user",user);
        return "switch-case";
    }
}
