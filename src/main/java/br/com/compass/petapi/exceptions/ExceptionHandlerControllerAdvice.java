package br.com.compass.petapi.exceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
  public static final String METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE = "Invalid Field: '%s'. Cause: '%s'.";

  @ExceptionHandler(PetNotFoundException.class)
  protected ResponseEntity<Object> handlePetNotFoundExceptions(PetNotFoundException ex) {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), List.of(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  @ExceptionHandler(DuplicatedPetException.class)
  public ResponseEntity<Object> handleDuplicatedPetException(DuplicatedPetException ex) {
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(),  List.of(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleInvalidUUID(IllegalArgumentException ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(),  List.of(ex.getMessage()));
    return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
    WebRequest request) {
    List<String> errorMessageList = getErrorMessages(ex.getBindingResult());
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), errorMessageList);
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  private List<String> getErrorMessages(BindingResult bindingResult) {
    List<String> errorMessages = new ArrayList<>();
    bindingResult.getFieldErrors().forEach(error ->
      errorMessages.add(getMethodArgumentNotValidErrorMessage(error)));
    bindingResult.getGlobalErrors().forEach(error ->
      errorMessages.add(getMethodArgumentNotValidErrorMessage(error)));
    return errorMessages;
  }
  private String getMethodArgumentNotValidErrorMessage(FieldError error) {
    return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE, error.getField(), error.getDefaultMessage());
  }

  private String getMethodArgumentNotValidErrorMessage(ObjectError error) {
    return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE, error.getObjectName(), error.getDefaultMessage());
  }

}
