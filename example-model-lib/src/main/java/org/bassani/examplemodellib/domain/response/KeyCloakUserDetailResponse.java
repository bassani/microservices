package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakUserDetailResponse implements Serializable {

    private static final long serialVersionUID = 4528569612928896403L;

    private String id;
    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private KeyCloakUserAttributeDetailResponse attributes;
    private Long notBefore;
}
