package org.bassani.examplemodellib.domain.entity.dbjor;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_TIPO_DESCONTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountTypeEntity implements Serializable {

	private static final long serialVersionUID = 4560669622197744634L;

	@Id
	@Column(name = "CD_TIPO_DESCONTO")
	private Integer id;
	
	@Column(name = "DS_TIPO_DESCONTO")
	private String name;
	
	@Column(name = "FL_ATIVO")
	private Boolean active;
	
	@Column(name = "DT_CADASTRO")
	private LocalDate registerDate;
	
    @Column(name = "CD_OPERADOR_CADASTRO")
    private Long registerOperator;

    @Column(name = "DT_ATUALIZACAO")
    private LocalDateTime updateDate;

    @Column(name = "CD_OPERADOR_ATUALIZACAO")
    private Long updateOperator;
}
