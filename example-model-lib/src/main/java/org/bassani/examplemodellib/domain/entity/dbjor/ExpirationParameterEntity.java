package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_PARAM_EXPIRACAO")
@SequenceGenerator(name = "SQ_TBM_PARAM_EXPIRACAO", sequenceName = "SQ_EXPIRACAO", allocationSize = 1)
public class ExpirationParameterEntity implements Serializable {

    private static final long serialVersionUID = 3905469778230366976L;

    @Id
    @Column(name = "ID_EXPIRACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TBM_PARAM_EXPIRACAO")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_FLUXO", foreignKey = @ForeignKey(name = "FK_TB_PARAM_EXPIRACAO_01"))
    private ExpirationEntity expiration;

    @Column(name = "QT_DIA_EXPIRACAO", nullable = false, precision = 3)
    private Integer qtyExpirationDay;

    @CreationTimestamp
    @Column(name = "DT_ATUALIZACAO", nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "DS_JUSTIFICATIVA", length = 500)
    private String description;

    @Column(name = "ID_KEYCLOAK_USER", nullable = false, length = 50)
    private String userId;

}
