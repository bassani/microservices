package org.bassani.examplemodellib.domain.entity.redshift;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.WeeklyForecastPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@IdClass(WeeklyForecastPrimaryKey.class)
@Entity
@Table(name = "ds_forecast_semanal", schema = "rd_dw_datascience")
public class WeeklyForecastEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cd_periodo_dia")
	private Long dayPeriodId;

    @Id
	@Column(name = "cd_data_alvo")
	private Long targetDateId;

    @Id
	@Column(name = "n_passo")
	private Long stepNumber;

    @Id
	@Column(name = "cd_regional")
	private Long distributionCenterId;

    @Id
	@Column(name = "cd_produto")
	private Long productId;

	@Column(name = "qt_predicao")
	private Long predictionQt;

	@Column(name = "vl_erro")
	private Double errorVl;

	@Column(name = "dt_inclusao")
	private Date inclusionDate;

	@Column(name = "dt_atualizacao")
	private Date updateDate;
}
