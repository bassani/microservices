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

@Entity
@Table(name = "TB_SIMULACAO_FLUXO_APROVACAO_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SimulationApprovalStatusFlowEntity implements Serializable {

    private static final long serialVersionUID = 8552935997663160576L;

    @Id
    @Column(name = "CD_SIMULACAO_FLUXO_APROVACAO_STATUS", length = 12)
    private Long id;

    @Column(name = "DS_SIMULACAO_FLUXO_APROVACAO_STATUS", length = 100)
    private String description;

}
