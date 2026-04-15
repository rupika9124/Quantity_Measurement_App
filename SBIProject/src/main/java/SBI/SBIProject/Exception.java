package SBI.SBIProject;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception {

    @ExceptionHandler(IllegalArgumentException.class)
    public String ex(IllegalArgumentException e)
    {
        return e.getMessage();
    }
}
