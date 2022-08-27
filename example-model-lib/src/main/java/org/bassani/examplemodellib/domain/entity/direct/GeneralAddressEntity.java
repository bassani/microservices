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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_ENDERECO_GERAL")
public class GeneralAddressEntity implements Serializable {

	private static final long serialVersionUID = 6061005871743076792L;

	@Id
	@Column(name = "NR_ENDERECO_GERAL")
	public Long id;

	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;
	
	@Column(name = "DS_ENDERECO")
	public String address;

	@Column(name = "NR_ENDERECO")
	private String number;

	@Column(name = "DS_COMPLEMENTO")
	private String complement;

	@Column(name = "DS_BAIRRO")
	private String district;

	@Column(name = "DS_CIDADE")
	private String city;
	
	@Column(name = "NR_CEP")
	private String zipCode;

	@Column(name = "SG_ESTADO")
	private String state;
}
