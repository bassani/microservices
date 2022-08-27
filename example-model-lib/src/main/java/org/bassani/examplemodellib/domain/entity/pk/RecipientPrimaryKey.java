package org.bassani.examplemodellib.domain.entity.pk;

import br.com.example.purchasesimulatormodellib.domain.entity.notify.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipientPrimaryKey implements Serializable {

    private static final long serialVersionUID = -960579453290565000L;

    private MessageEntity message;

    private String keycloakUserId;

}
