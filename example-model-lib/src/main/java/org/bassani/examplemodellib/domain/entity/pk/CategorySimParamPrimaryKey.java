package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class CategorySimParamPrimaryKey implements Serializable {

    private static final long serialVersionUID = 8246326198104011372L;
    private Long simulationParameters;
	
	private Long id;
}
