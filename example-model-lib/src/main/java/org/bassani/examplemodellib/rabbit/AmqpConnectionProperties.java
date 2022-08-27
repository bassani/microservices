package org.bassani.examplemodellib.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmqpConnectionProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

}
