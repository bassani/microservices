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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_EXPIRACAO")
public class ExpirationEntity implements Serializable {

    private static final long serialVersionUID = 5561284656361679315L;

    @Id
    @Column(name = "CD_FLUXO")
    private Integer id;

    @Column(name = "DS_FLUXO", nullable = false, length = 100)
    private String description;

}
