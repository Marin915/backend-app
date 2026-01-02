
package mx.insabit.ValidacionMateriales.Excepciones;


import mx.insabit.ValidacionMateriales.Response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFound(NotFoundException ex) {
        return new ApiResponse<>(false, ex.getMessage(), null);
    }
}