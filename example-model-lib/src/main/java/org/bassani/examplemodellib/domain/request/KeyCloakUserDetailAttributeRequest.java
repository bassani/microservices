package org.bassani.examplemodellib.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyCloakUserDetailAttributeRequest implements Serializable {

    private static final long serialVersionUID = -3559801581837473705L;

    @JsonProperty("CD_OPERADOR")
    private Long cdOperador;

    @JsonProperty("CD_PERFIL")
    private Long cdPerfil;

    @JsonProperty("CD_AREA")
    private Long cdArea;

    @JsonProperty("NM_TELEFONE")
    private String phoneNumer;

    @JsonProperty("DT_RETORNO_FERIAS")
    private LocalDate vacationReturnDate;

    @JsonProperty("NR_MATRICULA")
    private Long registrationNumber;
}
