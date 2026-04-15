package SBI.SBIProject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    Service ser;


    @PostMapping("/signup")
    public String sign(@RequestBody User u)
    {
        return ser.add(u);
    }

    @PostMapping("/login")
    public String log(@RequestBody User u)
    {
        return ser.log(u);
    }

    @PostMapping("/deposit/{n}")
    public String deposit(@PathVariable double n) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ser.deposit(email, n);
    }

    @PostMapping("/withd/{n}")
    public String withdraw(@PathVariable double n) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ser.withdraw(email, n);
    }

    @GetMapping("/balance")
    public String checkBalance() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ser.checkBalance(email);
    }

    @DeleteMapping("/close-account")
    public String closeAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ser.close(email);
    }
}
