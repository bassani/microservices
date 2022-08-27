package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "TB_PEDIDO_COMPRA_CLASSIF")
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationEntity implements Serializable {

    private static final long serialVersionUID = 289651482843639075L;

    @Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SequenceTbPedidoCompraClassificacao")
    @SequenceGenerator(name = "SequenceTbPedidoCompraClassificacao", 
    				   sequenceName = "SQ_PEDIDO_COMPRA_CLASSIF", 
    				   allocationSize = 1,
    				   initialValue = 23)
    @Column(name = "CD_PEDIDO_COMPRA_CLASSIF", length = 6)
    private Long id;

    @Column(name = "DS_PEDIDO_COMPRA_CLASSIF", length = 50)
    private String name;

    @Column(name = "FL_ATIVO")
    private Boolean active;

    @Column(name = "DT_CADASTRO")
    private LocalDateTime registerDate;

    @Column(name = "CD_OPERADOR_CADASTRO")
    private Long registerOperator;

    @Column(name = "DT_ATUALIZACAO")
    private LocalDateTime updateDate;

    @Column(name = "CD_OPERADOR_ATUALIZACAO")
    private Long updateOperator;

    @Column(name = "DS_DET_PED_COMPRA_CLASSIF")
    private String description;
    
    public String getStatus() {
    	if(this.active)
    		return "habilitado";
    	else
    		return "desabilitado";
    }
}
