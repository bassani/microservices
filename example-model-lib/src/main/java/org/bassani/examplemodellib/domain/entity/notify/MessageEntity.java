package org.bassani.examplemodellib.domain.entity.notify;

import br.com.example.purchasesimulatormodellib.domain.dto.MessageDTO;
import br.com.example.purchasesimulatormodellib.util.NotificationMessageConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_MENSAGEM")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = -6773288795550273940L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceTbMessage")
    @SequenceGenerator(name = "SequenceTbMessage", sequenceName = "SQ_MENSAGEM", allocationSize = 1)
    @Column(name = "ID_MENSAGEM")
    private Long id;

    @Lob
    @Column(name = "DOC_MENSAGEM", nullable = false)
    @Convert(converter = NotificationMessageConverter.class)
    private MessageDTO message;

    @CreationTimestamp
    @Column(name = "DT_NOTIFICACAO", nullable = false)
    private LocalDateTime notificationDate;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "message")
    private List<RecipientEntity> recipients;

    @Formula("json_value(DOC_MENSAGEM, '$.expirationDate')")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;

    @PrePersist
    public void updateChildEntities() {
        if (Objects.nonNull(getRecipients())) getRecipients().forEach(recipient -> recipient.setMessage(this));
    }

}
