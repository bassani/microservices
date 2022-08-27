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
public class KeyCloakGroupDetailResponse implements Serializable {

    private static final long serialVersionUID = 1819045315066068065L;

    private String id;
    private String name;
    private String path;
}
