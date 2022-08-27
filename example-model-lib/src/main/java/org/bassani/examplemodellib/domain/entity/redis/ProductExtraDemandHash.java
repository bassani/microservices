package org.bassani.examplemodellib.domain.entity.redis;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.base.HashKey;
import br.com.example.purchasesimulatormodellib.domain.response.ExtraDemandResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ProductExtraDemandHash {

    private HashKey hashKey;
    private ExtraDemandResponse purchaseExtraDemandResponse;

}
