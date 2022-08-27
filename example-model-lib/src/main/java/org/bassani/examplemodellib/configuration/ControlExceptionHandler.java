package org.bassani.examplemodellib.configuration;

import br.com.example.purchasesimulatormodellib.configuration.traceid.UniqueTrackingNumberFilter;
import br.com.example.purchasesimulatormodellib.exception.BusinessException;
import br.com.example.purchasesimulatormodellib.exception.ExceptionResolver;
import br.com.example.purchasesimulatormodellib.exception.Exceptions;
import br.com.example.purchasesimulatormodellib.exception.InvalidIdException;
import br.com.example.purchasesimulatormodellib.util.MessageBundle;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;

@Slf4j
@ControllerAdvice
public class ControlExceptionHandler {

    public static final String X_RD_TRACEID = "X-rd-traceid";

    private final String validationFailure = MessageBundle.getMessage("validation.failure.message");
    private final String notReadable = MessageBundle.getMessage("malformed_json.message");
    private final String notConvertible = MessageBundle.getMessage("json_conversion_error.message");


    private String getTraceID() {
        String traceId = Optional.ofNullable(MDC.get(UniqueTrackingNumberFilter.TRACE_ID_KEY))
                .orElse("");
        traceId = Optional.ofNullable(traceId.trim()
                .isEmpty() ? null : traceId)
                .orElse("not available");

        return traceId;
    }

    /**
     * Lida com exceções que não são subclasses dos outros handlers abaixo.
     * @param eThrowable Exceção ocorrida.
     * @return JSON com mensagem e descrição.
     */
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(Throwable eThrowable) {
        log.error("event=exception rootCauseOfException={}", ExceptionResolver.getRootException(eThrowable));

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(Optional.ofNullable(eThrowable.getMessage())
                        .orElse(eThrowable.toString()))
                .description(ExceptionResolver.getRootException(eThrowable))
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    /**
     * Lida com as exceções ocorridas no MS alvo (ao qual FeignClient fez a requisição). Também com conexão recusada
     * caso o  MS esteja fora do ar.
     *
     * @param feignException Exceção Feign.
     * @return JSON com lista de descrições, mensagem e caminho (opcional).
     */
    @ExceptionHandler({FeignException.class})
    public ResponseEntity<Object> handleFeignException(FeignException feignException, ServletWebRequest request) {
        log.error("event=exception feignException={} sourceRequest={}", feignException.toString(),
                requireNonNullElse(request.getRequest().getRequestURI(), "N/A"));

        Optional<BusinessException> ex = Exceptions.fromFeignException(feignException);
        if (ex.isEmpty())
            return handleException(feignException);

        BusinessException businessException = ex.get();

        var responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(businessException.getHttpStatusCode())
                .headers(responseHeaders)
                .body(businessException);
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(BusinessException ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());
        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    /**
     * Captura exceção lançada por classes anotadas com {@link org.springframework.validation.annotation.Validated
     * Validated} quando o argumento anotado com {@link br.com.example.purchasesimulatormodellib.constraints.ValidId ValidId}
     * for inválido.
     *
     * @return {@link InvalidIdException} serializada como JSON.
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exMethod,
                                                                   WebRequest request) {
        var id = requireNonNullElse(exMethod.getValue(), "").toString();
        BusinessException ex = new InvalidIdException(id);
        var responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    /**
     * @param exMethod
     * @param request
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exMethod, WebRequest request) {
        BusinessException ex = Exceptions.fromConstraintViolations(exMethod.getConstraintViolations());
        var responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    /**
     * Lida com argumentos que falham na validação.
     *
     * @param exMethod
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod, ServletWebRequest request) {
        BindingResult bindingResult = exMethod.getBindingResult();
        List<String> errors = new ArrayList<>();
        errors.addAll(fieldErrors(bindingResult));
        errors.addAll(globalErrors(bindingResult));

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(validationFailure)
                .path(request.getRequest()
                        .getRequestURI())
                .descriptions(errors)
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    private List<String> fieldErrors(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return fieldErrors.stream()
                .map(f -> f.getField()
                        .concat(":")
                        .concat(requireNonNullElse(f.getDefaultMessage(), "")))
                .collect(Collectors.toList());
    }

    private List<String> globalErrors(BindingResult bindingResult) {
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

        return globalErrors.stream()
                .map(f -> f.getObjectName()
                        .concat(":")
                        .concat(requireNonNullElse(f.getDefaultMessage(), "")))
                .collect(Collectors.toList());
    }

    /**
     * Lida com problemas do Jackson ao deserializar um JSON.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> jsonParsingError(HttpMessageNotReadableException exMethod,
                                                   ServletWebRequest request) {

        Throwable mostSpecificCause = exMethod.getMostSpecificCause();
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .path(request.getRequest()
                        .getRequestURI())
                .message(notReadable)
                .description(mostSpecificCause.getMessage())
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    /**
     * Lida com erros do Jackson ao converter valor JSON para tipo Java.
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> jsonConversionError(HttpMessageConversionException exMethod,
                                                      ServletWebRequest request) {

        Throwable mostSpecificCause = exMethod.getMostSpecificCause();
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .path(request.getRequest()
                        .getRequestURI())
                .message(notConvertible)
                .description(mostSpecificCause.getMessage())
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleException(MissingServletRequestParameterException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(Optional.ofNullable(e.getMessage())
                        .orElse(e.toString()))
                .description(ExceptionResolver.getRootException(e))
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException e) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
                .message(Optional.ofNullable(e.getMessage())
                        .orElse(e.toString()))
                .description(ExceptionResolver.getRootException(e))
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, this.getTraceID());

        return ResponseEntity.status(ex.getHttpStatusCode())
                .headers(responseHeaders)
                .body(ex);
    }
}
