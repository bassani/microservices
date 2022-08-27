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
@Table(name = "TB_TIPO_VERBA")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BudgetTypeEntity implements Serializable {

    private static final long serialVersionUID = -8291217161237391422L;

    @Id
	@Column(name = "CD_TIPO_VERBA")
	private Integer id;
	
	@Column(name = "DS_TIPO_VERBA")
	private String name;
	
	@Column(name = "FL_ATIVO")
	private Boolean active;

    @Column(name = "CD_OPERADOR_CADASTRO")
    private Long registerOperator;

    @Column(name = "DT_CADASTRO")
	private LocalDate registerDate;

    @Column(name = "CD_OPERADOR_ALTERACAO")
    private Long updateOperator;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime updateDate;

}
