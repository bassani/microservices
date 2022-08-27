package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipientMessageReadUnReadRequest implements Serializable {

    private static final long serialVersionUID = 122381256510867884L;

    @NotEmpty(message = Constants.VALID_ANNOTATION_NOT_EMPTY_LIST)
    @ApiModelProperty(value = "IDs das mensagens", example = "[1,2,3]")
    private List<Long> ids;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do usuário no KeyCloak", example = "553046d0-6365-46ca-999a-8c6a27c87cb8")
    private String keycloakUserId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Status da Mensagem (Lida/Não Lida)", example = "true")
    private Boolean read;

}
