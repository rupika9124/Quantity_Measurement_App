package SBI.SBIProject;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTBuilder jwt;

    @Autowired
    UserService user;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String a = request.getHeader("Authorization");
//        if(a == null)
//        {
//            filterChain.doFilter(request,response);
//        }
//        try
//        {
//            jwt.extractUserName(a);
//        }
//        catch (Exception e)
//        {
//
//        }

        System.out.println("WELCOME TO JWT FILTER CLASS");
        String username=null;
        String details = request.getHeader("Authorization");
        System.out.println("printed jwt "+ details);

        String path = request.getRequestURI();

        if(details==null) {
            filterChain.doFilter(request,response);
            return;}

        String token= details.substring(7);
        System.out.println("token"+ token);
        try {

            username = jwt.extractUserName(token);
        }
        catch(ExpiredJwtException expiredJwtException){
            System.out.println("Expired JWT");
            filterChain.doFilter(request,response);
            return;
        }
        //  System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        System.out.println("TOKEN VALID");
        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails= user.loadUserByUsername(username);
            System.out.println(userDetails.getAuthorities().toString());

            if (jwt.validateToken(token,userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }



        }

        filterChain.doFilter(request,response);

    }
}




