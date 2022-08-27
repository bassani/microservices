package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseBiNotFoundException extends NotFoundException {
    public static final String MESSAGE = "Dados do BI não encontrado!";
    public static final String DESCRIPTION = "Não foi possível retornar dados do BI.";

    public PurchaseBiNotFoundException() {
        super(MESSAGE, DESCRIPTION);
        
    }
}