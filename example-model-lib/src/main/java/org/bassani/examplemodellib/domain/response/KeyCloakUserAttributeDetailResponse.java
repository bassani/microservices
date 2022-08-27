package org.bassani.examplemodellib.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakUserAttributeDetailResponse implements Serializable {

    private static final long serialVersionUID = 7827103479965099018L;

    @JsonProperty("CD_OPERADOR")
    private List<Long> cdOperador;

    @JsonProperty("CD_PERFIL")
    private List<Long> cdPerfil;

    @JsonProperty("CD_AREA")
    private List<Long> cdArea;

    @JsonProperty("NM_TELEFONE")
    private List<String> phoneNumer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("DT_RETORNO_FERIAS")
    private List<LocalDate> vacationReturnDate;

    @JsonProperty("NR_MATRICULA")
    private List<Long> registrationNumber;
}
