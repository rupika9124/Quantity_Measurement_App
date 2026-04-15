package SBI.SBIProject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    UserRepo r;

    @Autowired
    PasswordEncoder pass;

    @Autowired
    AuthenticationManager auth;

    @Autowired
    JWTBuilder jwt;

    public String add(User a)
    {
        if(a.getBalance() < 1000)
        {
            return "Minimum 1000Rs required";
        }

        a.setPassword(pass.encode(a.getPassword()));
        a.setRole("USER");

        User save = r.save(a);
        String accNo = "SBI" + String.format("%08d",save.getId());
        save.setAccountNumber(accNo);

        r.save(a);

        return "Account Created. Account No:"+accNo;
    }


    public String log(User u)
    {
        UsernamePasswordAuthenticationToken x = new UsernamePasswordAuthenticationToken(u.getEmail(),u.getPassword());
        Authentication a;
        try
        {
            a = auth.authenticate(x);
        }
        catch (BadCredentialsException e)
        {
            return "Invalid Credentials";
        }
        return "Login Successful Your Token is :"+jwt.generateToken(u.getEmail());

    }

    public String deposit(String email,double amt)
    {
        User c = r.findByEmail(email);
        c.setBalance(c.getBalance() + amt);
        r.save(c);
        return "Balance: " + c.getBalance();

    }

    public String withdraw(String email,double amt)
    {
        User a = r.findByEmail(email);
        if(amt <= 0)
        {
            throw new IllegalArgumentException("Amount is not valid");
        }
        if (a.getBalance() < amt) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        a.setBalance(a.getBalance() - amt);
        r.save(a);
        return "Rs "+amt+" withdrawn Successfully .... Balance : "+a.getBalance();
    }

    public String checkBalance(String email) {
        User c = r.findByEmail(email);
        return "Account: " + c.getAccountNumber() + " | Balance: ₹" + c.getBalance();
    }

    public String close(String email) {

        User c = r.findByEmail(email);
        r.delete(c);

        return "Account closed";
    }


}
