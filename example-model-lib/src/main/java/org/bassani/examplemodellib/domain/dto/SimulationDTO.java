package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.constraints.AtLeastOneNotEmpty;
import br.com.example.purchasesimulatormodellib.constraints.AtMostOneNotNull;
import br.com.example.purchasesimulatormodellib.domain.request.CategoryRequest;
import br.com.example.purchasesimulatormodellib.domain.request.DistributionCenterRequest;
import br.com.example.purchasesimulatormodellib.domain.request.OrderTypeRequest;
import br.com.example.purchasesimulatormodellib.domain.request.ProductFilterRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimCalculationBasisRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimClassificationRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimNewPaymentTermRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SubCategoryRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SupplierRequest;
import br.com.example.purchasesimulatormodellib.enums.SimulationStatusEnum;
import br.com.example.purchasesimulatormodellib.enums.SimulationTypeEnum;
import br.com.example.purchasesimulatormodellib.rabbit.EventMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@AtLeastOneNotEmpty({"suppliers", "categories"})
@AtMostOneNotNull({"generalDiscount", "productDiscounts"})
public class SimulationDTO implements EventMessage {

    private static final long serialVersionUID = 7239656966528489414L;

    private Long id;

    @NotEmpty
    @Valid
    private List<DistributionCenterRequest> distributionCenters;

    @Valid
    private List<SupplierRequest> suppliers;

    @Valid
    private List<CategoryRequest> categories;

    @Valid
    private List<SubCategoryRequest> subcategories;

    @NotNull
    @Valid
    private OrderTypeRequest orderType;

    @NotNull
    @Valid
    private SimClassificationRequest classification;

    @Valid
    private GeneralDiscountDTO generalDiscount;

    @Valid
    private List<ProductDiscountDTO> productDiscounts;

    @Valid
    private ProductFilterRequest productFilter;

    @NotNull
    private SimulationTypeEnum simulationType;

    @FutureOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anticipationDate;

    @Valid
    private SimNewPaymentTermRequest newPaymentTerm;

    @NotBlank
    private String note;

    @NotNull
    @Valid
    private SimCalculationBasisRequest calculationBasis;

    @NotNull
    @Positive
    private Long operatorId;

    private String keycloakUserId;

    @Valid
    private BudgetDTO budget;

    private SimulationStatusEnum simulationStatus;

    @CreationTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime registerDate;
    
    private Long stockCoverageDC;

    @Positive
    private BigDecimal amount;

}
