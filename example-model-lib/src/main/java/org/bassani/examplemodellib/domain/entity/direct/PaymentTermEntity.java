package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
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
@Table(name = "TB_CONDICAO_PAGTO")
public class PaymentTermEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_CONDICAO_PAGTO", nullable = false)
	private Long id;

	@Column(name = "DS_CONDICAO_PAGTO", nullable = false, length = 50)
	private String name;
	
	@Column(name = "QT_PARCELAS")
	private Integer qtdPracela;
	
	@Column(name = "PC_DESCONTO")
	private BigDecimal pcDesconto;

}