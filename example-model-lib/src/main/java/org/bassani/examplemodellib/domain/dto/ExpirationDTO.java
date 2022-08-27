package org.bassani.examplemodellib.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpirationDTO {

    @NotNull
    @ApiModelProperty(value = "ID do fluxo de expiração", example = "1")
    private Long id;

    @NotBlank
    @Length(max = 100)
    @ApiModelProperty(value = "Descrição do fluxo de expiração", example = "Fluxo de simulacao")
    private String description;

}
