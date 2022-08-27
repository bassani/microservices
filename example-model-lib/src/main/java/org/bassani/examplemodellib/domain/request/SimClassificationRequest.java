package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimClassificationRequest implements Serializable {

    private static final long serialVersionUID = -7488922657230165581L;

    @ApiModelProperty(value = "Código do classificação", example = "10")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Nome da classificação", example = "PBM")
    @Size(max = 50)
    @NotBlank
    private String name;
}
