package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ProductRequest {
    @ApiModelProperty(value = "Código(s) do(s) produto(s)", example = "7381")
    @Positive
    @NotNull
    private List<Long> codes;
}
