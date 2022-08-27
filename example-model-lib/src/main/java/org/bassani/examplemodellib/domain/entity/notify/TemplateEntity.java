package org.bassani.examplemodellib.domain.entity.notify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_TEMPLATE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TemplateEntity implements Serializable {

    private static final long serialVersionUID = 7160772733750938263L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceTbTemplate")
    @SequenceGenerator(name = "SequenceTbTemplate", sequenceName = "SQ_TEMPLATE", allocationSize = 1)
    @Column(name = "ID_TEMPLATE")
    private Long id;

    @Column(name = "DS_TEMPLATE", nullable = false)
    private String description;

}
