package org.bassani.examplemodellib.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {

    @ApiModelProperty(value = "ID do Produto", example = "74333")
    private Long productId;

    @ApiModelProperty(value = "Descrição do produto", example = "TRACTA MASC REGENER 60")
    private String productDescription;

    @ApiModelProperty(value = "Código da categoria", example = "792")
    private Long categoryId;

    @ApiModelProperty(value = "Código da Sub-Categoria", example = "5")
    private Long subCategoryId;

    @ApiModelProperty(value = "Flag de ativo", example = "1 ou 0")
    private Boolean isInactive;

    @ApiModelProperty(value = "Flag de Kit promo", example = "1 ou 0")
    private Boolean isKitPromo;

    @ApiModelProperty(value = "Porcentagem do desconto comercial", example = "8.22")
    private Double commercialDiscountPc;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiModelProperty(value = "Data de criação do produto", example = "01/01/2021")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @ApiModelProperty(value = "Código do produto no provedor", example = "8.22")
    private String productProviderCode;

    @ApiModelProperty(value = "ID do SubGrupo", example = "8.22")
    private Integer subGroupId;

    @ApiModelProperty(value = "EAN", example = "8.22")
    private Long ean;

    @ApiModelProperty(value = "Curva", example = "8.22")
    private String curveFis;

    @ApiModelProperty(value = "Flag de temporário inativo", example = "0, 1 ou 2")
    private Long temporaryInactiveCode;
}
