package org.bassani.examplemodellib.domain.entity.redis.key.base;

import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class HashKey {

    protected static final String PREFIX = "purchase:";

    @Getter
    protected String key;

    @Getter
    protected String field;

}
