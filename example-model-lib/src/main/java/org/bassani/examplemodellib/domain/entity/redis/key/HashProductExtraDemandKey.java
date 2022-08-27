package org.bassani.examplemodellib.domain.entity.redis.key;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.base.HashKey;
import lombok.Builder;
import lombok.Getter;

public class HashProductExtraDemandKey extends HashKey {

    private final String PREFIX = super.PREFIX + "product:extrademand:";

    @Getter
    private Long distributionCenterId;
    @Getter
    private Long productId;

    @Builder
    public HashProductExtraDemandKey(Long distributionCenterId, Long productId) {
        this.key = PREFIX + distributionCenterId.toString();

        String fields = "";
        if (productId != null) {
            fields = productId.toString();
        }
        this.field = fields;
    }
}