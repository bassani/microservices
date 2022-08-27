package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "TB_OPERADOR")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_OPERADOR", nullable = false)
    private Long code;

    @Column(name = "NM_OPERADOR", nullable = false)
    private String name;

    @Column(name = "DS_EMAIL")
    private String email;

    @Column(name = "CD_AREA")
    private Long areaCode;

    @Column(name = "CD_CARGO")
    private Long positionCode;

    @Column(name = "DT_DEMISSAO")
    private LocalDate resignationDate;

}
