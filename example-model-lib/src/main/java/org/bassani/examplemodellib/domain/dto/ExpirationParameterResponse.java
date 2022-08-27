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

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpirationParameterResponse {

    @ApiModelProperty(value = "ID do parâmetro de expiração", example = "1")
    private Long id;

    @ApiModelProperty(value = "Fluxo de expiração", example = "1")
    private ExpirationDTO expirationFlow;

    @ApiModelProperty(value = "Quantidade de dias para expiração", example = "1")
    private Integer qtyExpirationDay;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "Data de criação do parametro")
    private LocalDateTime creationDateTime;

    @ApiModelProperty(value = "Descrição do parametro")
    private String description;

    @ApiModelProperty(value = "ID do usuário que criou o parametro")
    private String userId;

}
