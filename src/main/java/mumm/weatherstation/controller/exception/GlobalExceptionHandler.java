package mumm.weatherstation.controller.exception;

import mumm.weatherstation.controller.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<Void> handleNotFound(NoSuchElementException ex) {
        return Response.error("not_found", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        Object value = ex.getValue();
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";

        String message = String.format("Invalid value '%s' for field '%s'. Expected type: %s", value, field,
                expectedType);

        return Response.error("invalid_type", message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleInvalidJson(HttpMessageNotReadableException ex) {
        return Response.error(
                "invalid_json",
                "Request body contains invalid data type"
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> handleGeneric(Exception ex) {
        return Response.error("internal_server_error", "Unexpected error occurred");
    }

}
