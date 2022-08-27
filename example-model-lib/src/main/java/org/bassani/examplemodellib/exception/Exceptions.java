package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.constraints.ValidId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Exceptions {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Exceptions() {
        throw new AssertionError("Should not be instantiated.");
    }

    private static Optional<ConstraintViolation<?>> findConstraintViolation(Set<ConstraintViolation<?>> violations,
                                                                            Class<? extends Annotation> constraint) {
        return violations.stream()
                .filter(v -> v.getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .equals(constraint))
                .findAny();
    }

    private static BusinessException defaultBusinessExceptionFrom(Set<ConstraintViolation<?>> constraintViolations) {
        var message = "Violação de restrição!";
        String description = constraintViolations.stream()
                .map(v -> v.getRootBeanClass()
                        .getName() + " " + v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining());

        return BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(message)
                .description(description)
                .build();
    }

    public static BusinessException fromConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        Optional<ConstraintViolation<?>> idViolation = findConstraintViolation(constraintViolations, ValidId.class);
        if (idViolation.isPresent()) {
            return new InvalidIdException((int) idViolation.get()
                    .getInvalidValue());
        }

        return defaultBusinessExceptionFrom(constraintViolations);
    }

    /** Tenta extrair o JSON de erro vindo do MS onde o erro ocorreu.
     *
     * @param feignException FeignException.
     * @return BusinessException a ser serializada em JSON.
     */
    public static Optional<BusinessException> fromFeignException(FeignException feignException) {
        try {
            String body = Objects.requireNonNull(feignException.contentUTF8());
            var jsonNode = OBJECT_MAPPER.readTree(body);

            String message = jsonNode.get("message")
                    .asText();

            String description = jsonNode.has("description") ? jsonNode.get("description")
                    .asText("") : "";

            String path = jsonNode.has("path") ? jsonNode.get("path")
                    .asText("") : "";

            List<String> descriptions = new ArrayList<>();
            if (jsonNode.has("descriptions")) {
                for (JsonNode d : jsonNode.get("descriptions")) {
                    descriptions.add(d.textValue());
                }
            }
            BusinessException ex = BusinessException.builder()
                    .httpStatusCode(HttpStatus.valueOf(feignException.status()))
                    .code(String.valueOf(feignException.status()))
                    .message(message)
                    .path(path.isBlank() ? null : path)
                    .description(description.isBlank() ? null : description)
                    .descriptions(descriptions.isEmpty() ? null : descriptions)
                    .build();
            return Optional.of(ex);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
