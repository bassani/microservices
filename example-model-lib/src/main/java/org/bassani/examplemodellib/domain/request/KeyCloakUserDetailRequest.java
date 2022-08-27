package org.bassani.examplemodellib.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyCloakUserDetailRequest implements Serializable {

    private static final long serialVersionUID = -6557345990719471747L;

    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private KeyCloakUserDetailAttributeRequest attributes;
    private Long notBefore;

}
