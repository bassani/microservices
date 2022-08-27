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
@Table(name = "TB_AREA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AreaEntity implements Serializable {

    private static final long serialVersionUID = -6662386691026094099L;

    @Id
    @Column(name = "CD_AREA", length = 4)
    private Long id;

    @Column(name = "DS_AREA", length = 50)
    private String description;

}
