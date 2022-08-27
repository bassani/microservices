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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_BIF_ESTOQUE_DIARIO")
public class DailyStockBifEntity implements Serializable {

    private static final long serialVersionUID = 6315096220980581521L;

    @Id
	@Column(name = "CD_TP_ESTOQUE")
	private Long stockTypeCode;

	@Column(name = "QT_ESTOQUE")
	private Long stockQuantity;

    @Column(name = "VL_ESTOQUE")
    private BigDecimal stockValue;

    @Column(name = "CD_PERIODO_DIA")
    private Long dayPeriodCode;

    @Column(name = "CD_FILIAL")
    private Long branchCode;

}
