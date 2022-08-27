package org.bassani.examplemodellib.domain.entity.redis.key;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.base.HashKey;
import lombok.Builder;
import lombok.Getter;


public class HashAnticipationProductPurchaseDaysKey extends HashKey {

    private final String PREFIX = super.PREFIX + "product:purchasedays:anticipation:";

    @Getter
    private Long distributionCenterId;
    @Getter
    private Long productId;
    @Getter
    private Long supplierId;

    @Builder
    public HashAnticipationProductPurchaseDaysKey(Long distributionCenterId, Long supplierId, Long productId) {
        this.key = PREFIX + distributionCenterId.toString();

        String fields = "";
        if (supplierId!= null && productId != null) {
            fields = supplierId + ":" + productId;
        }
        this.field = fields;
    }

}
