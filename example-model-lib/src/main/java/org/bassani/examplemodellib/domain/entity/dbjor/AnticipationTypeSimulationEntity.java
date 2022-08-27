package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_SIMULACAO_TIPO_ANTECIPACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnticipationTypeSimulationEntity implements Serializable {

    private static final long serialVersionUID = -323588552850404171L;

    //Spring Data won't allow @OneToOne column to be @Id.
    @Column(name = "ID_SIMULACAO")
    @Id
    private Long simulationId;

    @ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_TIPO_ANTECIPACAO_01"))
    private SimulationParametersEntity simulationParameters;
	
	@Column(name = "DT_ANTECIPACAO")
	private LocalDate anticipationDate;
}