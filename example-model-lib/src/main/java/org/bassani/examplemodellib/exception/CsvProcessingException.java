package org.bassani.examplemodellib.exception;

import com.opencsv.exceptions.CsvException;

public class CsvProcessingException extends InternalServerErrorException {

    private static final String MESSAGE = "Erro ao processar CSV.";
    private static final String DESCRIPTION = "Verifique se o BD contém os dados obrigatórios do CSV.";

    public CsvProcessingException(CsvException e) {
        super(MESSAGE, DESCRIPTION, e);
    }
}
