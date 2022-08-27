package org.bassani.examplemodellib.csv;

import com.opencsv.bean.customconverter.ConverterLanguageToBoolean;

public class BooleanToStatusCsvConverter<T, I>  extends ConverterLanguageToBoolean<T, I>  {

    private static final String TRUE = "HABILITADO";
    private static final String FALSE = "DESABILITADO";
    private static final String[] TRUE_STRINGS = {TRUE};
    private static final String[] FALSE_STRINGS = {FALSE};

    @Override
    protected String getLocalizedTrue() {
        return TRUE;
    }

    @Override
    protected String getLocalizedFalse() {
        return FALSE;
    }

    @Override
    protected String[] getAllLocalizedTrueValues() {
        return TRUE_STRINGS;
    }

    @Override
    protected String[] getAllLocalizedFalseValues() {
        return FALSE_STRINGS;
    }
}