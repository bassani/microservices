package org.bassani.examplemodellib.domain.entity.redis.key;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.base.HashKey;
import lombok.Builder;
import lombok.Getter;

public class HashRoundingParamsKey extends HashKey {

    private final String PREFIX = super.PREFIX + "product:roundingparams:";

    @Getter
    private Long distributionCenterId;
    @Getter
    private Long supplierId;
    @Getter
    private Long productId;

    @Builder
    public HashRoundingParamsKey(Long distributionCenterId, Long supplierId, Long productId) {
        this.key = PREFIX + distributionCenterId.toString();

        String fields = "";
        if (supplierId != null && productId != null) {
            fields = supplierId + ":" + productId;
        }
        this.field = fields;
    }
}