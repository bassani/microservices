package org.bassani.examplemodellib.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpirationParameterRequest {

    @NotNull
    @Valid
    @ApiModelProperty(value = "ID do fluxo", example = "1")
    private Integer flowId;

    @NotNull
    @Min(1)
    @Max(5)
    @ApiModelProperty(value = "Quantidade de dias para expiração", example = "1")
    private Integer qtyExpirationDay;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "Data de criação do parametro")
    private LocalDateTime creationDateTime;

    @NotBlank
    @Length(min = 1, max = 500)
    @ApiModelProperty(value = "Descrição do parametro")
    private String description;

    @Length(min = 1, max = 50)
    @ApiModelProperty(value = "ID do usuário que criou o parametro")
    private String userId;

}
