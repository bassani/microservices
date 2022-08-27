package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;

import java.util.List;

public class OrdersNotFoundException extends NotFoundException {

    public OrdersNotFoundException(List<Long> orderNumbers) {
        super(String.format(MessageEnum.ORDERS_NUMBER_NOT_FOUND.getMessage(), orderNumbers), MessageEnum.ORDERS_NUMBER_NOT_FOUND.getDescription());
    }

    public OrdersNotFoundException(Long simulationId) {
        super(String.format(MessageEnum.ORDER_NOT_FOUND.getMessage(), simulationId),
                MessageEnum.ORDER_NOT_FOUND.getDescription());
    }
}
