package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.domain.request.OperatorRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimClassificationRequest;
import br.com.example.purchasesimulatormodellib.enums.SimulationStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -4558297904558509234L;

    @NotNull
    private SimulationStatusEnum simulationStatus;

    @NotNull
    private Long simulationId;

    @NotNull
    @Valid
    private String parentSupplierName;

    @NotNull
    @Valid
    private SimClassificationRequest classification;

    private OperatorRequest completedBy;

    @Size(max = 50)
    private String reason;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;

    @Positive
    private BigDecimal amount;
}
