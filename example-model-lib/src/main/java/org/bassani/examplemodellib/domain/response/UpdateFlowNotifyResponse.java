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
public class UpdateFlowNotifyResponse implements Serializable {

    private static final long serialVersionUID = 6841551204663209737L;
    private Boolean sendNotification;
}
