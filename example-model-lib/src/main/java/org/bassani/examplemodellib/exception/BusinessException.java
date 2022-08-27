package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"stackTrace", "suppressed", "localizedMessage"})
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5822971229740050718L;
    @JsonIgnore
    private HttpStatus httpStatusCode;
    @JsonIgnore
    private String code;
    private String path;
    private String message;
    private String description;
    private List<String> descriptions;

    public BusinessException(HttpStatus httpStatusCode, String message, String description) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.description = description;
    }


    public BusinessExceptionBody getOnlyBody() {
        return BusinessExceptionBody.builder()
                .code(this.code)
                .message(this.message)
                .description(this.description)
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BusinessExceptionBody {
        private String code;

        private String message;

        private String description;

    }
}
