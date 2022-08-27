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
@Table(name = "TB_PRODUTO")
public class ProductEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_PRODUTO")
	private Long productId;

	@Column(name = "DS_PRODUTO")
	private String productDescription;

	@Column(name = "CD_CATEGORIA")
	private Long categoryId;

	@Column(name = "CD_SUB_CATEGORIA")
	private Long subCategoryId;

	@Column(name = "FL_INATIVO")
	private Boolean isInactive;
	
	@Column(name = "DT_CADASTRO")
	private LocalDate dateCreated;
	
	@Column(name = "CD_SUBGRUPO")
	private Long subGroupId;
	
	@Column(name = "CL_CURVA_FIS")
	private String physicalCurve;

}
