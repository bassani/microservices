package org.bassani.examplemodellib.domain.entity.bi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_BID_PERIODO_DIA")
public class PeriodDayEntity implements Serializable {

    private static final long serialVersionUID = 2002359682710878016L;

    @Id
    @Column(name = "CD_PERIODO_DIA")
    private Long periodDayCode;

	@Column(name = "DT_PERIODO_DIA")
	private LocalDateTime periodDayDate;

	@Column(name = "CD_TIPO_FILIAL")
	private String branchTypeDescription;

    @Column(name = "QT_ESTOQUE")
    private Long stockQuantity;

    @Column(name = "VL_ESTOQUE")
    private BigDecimal stockValue;

}
