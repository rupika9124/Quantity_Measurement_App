package SBI.SBIProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo r;

    @Override
    public UserDetails loadUserByUsername(String s)
    {
        SBI.SBIProject.User c = r.findByEmail(s);
        if(c == null)
        {
            throw new UsernameNotFoundException("Email not Found");
        }

        String email = c.getEmail();
        String password = c.getPassword();
        List<GrantedAuthority> ab = new ArrayList<GrantedAuthority>();
        ab.add(new SimpleGrantedAuthority(c.getRole()));
        return new User(email,password,ab);
    }
}
