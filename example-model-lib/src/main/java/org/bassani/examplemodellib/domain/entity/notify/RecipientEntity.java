package org.bassani.examplemodellib.domain.entity.notify;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.RecipientPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@IdClass(RecipientPrimaryKey.class)
@Table(name = "TB_DESTINATARIO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipientEntity implements Serializable {

    private static final long serialVersionUID = 3536827736284567795L;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_MENSAGEM", foreignKey = @ForeignKey(name = "FK_TB_DESTINATARIO_01"))
    private MessageEntity message;

    @Id
    @Column(name = "ID_KEYCLOAK_USER", nullable = false)
    private String keycloakUserId;

    @Column(name = "CD_OPERADOR", nullable = false)
    private Long operatorID;

    @Column(name = "FL_VISUALIZADO", nullable = false)
    private Boolean read;

    @Transient
    private String email;

}
