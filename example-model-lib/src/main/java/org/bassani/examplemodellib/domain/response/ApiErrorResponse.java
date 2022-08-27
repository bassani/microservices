package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {

	private String message;
	
	private int code;
	
	private String status;
	
	private String objectName;
    
	private List<String> errors;

}
