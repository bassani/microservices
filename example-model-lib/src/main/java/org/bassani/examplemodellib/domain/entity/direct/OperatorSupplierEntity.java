package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.OperatorSupplierPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OperatorSupplierPrimaryKey.class)
@Table(name = "TB_FORNECEDOR_OPERADOR")
public class OperatorSupplierEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;

	@Id
	@Column(name = "CD_OPERADOR ")
	private Long operatorId;

	@Id
	@Column(name = "CD_REGIONAL")
	private Long regionalId;

	@Id
	@Column(name = "CD_FABRICANTE")
	private Long manufacturerId;
}
