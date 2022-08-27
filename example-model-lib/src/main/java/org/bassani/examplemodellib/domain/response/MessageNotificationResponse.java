package org.bassani.examplemodellib.domain.response;

import br.com.example.purchasesimulatormodellib.domain.dto.MessageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MessageNotificationResponse {

    @ApiModelProperty(value = "ID da Mensagem", example = "10")
    private Long id;

    @ApiModelProperty(value = "Variaveis da notificação", example = "{\"simulationStatus\":\"REPROVADO\"}")
    private MessageDTO message;

    @ApiModelProperty(value = "Data de notificação", example = "01/01/2021")
    private LocalDateTime notificationDate;

    @ApiModelProperty(value = "Data de expiração", example = "2021-01-31")
    private LocalDateTime expirationDate;
}
