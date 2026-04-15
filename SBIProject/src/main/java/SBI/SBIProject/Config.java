package SBI.SBIProject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Config {

    @Autowired
    JWTFilter jwt;

    @Bean
    public PasswordEncoder f() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager fu(AuthenticationConfiguration auth)
    {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain fun(HttpSecurity http)
    {
        return http.csrf(x->x.disable()).
                addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x->x.requestMatchers("/login","/signup")
                        .permitAll().anyRequest().authenticated()).build();
    }
}
