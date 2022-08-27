package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.PaymentTermInstallmentPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PaymentTermInstallmentPrimaryKey.class)
@Entity
@Table(name = "TB_CONDICAO_PAGTO_PARCELA")
public class PaymentTermInstallmentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_CONDICAO_PAGTO", nullable = false)
    private Long conditionPaymentCode;

	@Id
	@Column(name = "NR_SEQUENCIA", nullable = false)
	private Integer sequencyNumber;
	
	@Column(name = "QT_DIAS_PAGTO")
	private Integer daysQuantityPayment;
	
	@Column(name = "PC_PAGTO")
	private BigDecimal pcPayment;

}