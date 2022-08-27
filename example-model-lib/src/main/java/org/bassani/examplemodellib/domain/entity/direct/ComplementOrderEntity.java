package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PEDIDO_COMPLEMENTO")
public class ComplementOrderEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PEDIDO_COMPLEMENTO")
	private Long complementOrderId;

	@Column(name = "DS_PEDIDO_COMPLEMENTO")
	private String complementOrderDescription;

	@Column(name = "CD_PEDIDO_TIPO_COMPLEMENTO")
	private Long complementOrderTypeCode;

	@Column(name = "CD_REGIONAL")
	private Long regionalCode;

	@Column(name = "DT_INI_VIGENCIA")
	private LocalDate startDate;

	@Column(name = "DT_FIM_VIGENCIA")
	private LocalDate endDate;

	@Column(name = "DT_CADASTRO")
	private LocalDate registrationDate;

	@Column(name = "CD_OPERADOR_CADASTRO")
	private Long registrationOperatorCode;

	@Column(name = "CD_FILIAL")
	private Long branchCode;

    @Column(name = "CD_FILIAL_BASE")
    private Long baseBranchCode;

    @Column(name = "DT_ENCERRAMENTO")
    private LocalDate closingDate;

    @Column(name = "CD_PEDIDO_MOTIVO_ENCERRAMENTO")
    private Long orderCodeReasonClosing;

    @Column(name = "CD_OPERADOR_ENCERRAMENTO")
    private Long operatorClosingCode;
}
