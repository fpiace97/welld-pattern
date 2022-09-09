package controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.converter.HttpMessageNotReadableException;
import util.ManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionHandlerController implements ErrorController {

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception e) {
        String message;
        System.out.println(e.getMessage());
        if(e instanceof HttpMessageNotReadableException)
            message = "Bad Request check param";
        else
            message = "Exception";

        return ResponseEntity.ok(new ManageResponse("KO", message));
    }
}