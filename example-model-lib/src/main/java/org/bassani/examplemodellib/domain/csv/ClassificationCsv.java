package org.bassani.examplemodellib.domain.csv;

import br.com.example.purchasesimulatormodellib.converter.csv.BooleanToStatusCsvConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClassificationCsv {

    @CsvBindByName(column = "NOME CLASSIFICACAO", required = true)
    private String name;

    @CsvBindByName(column = "DESCRICAO CLASSIFICACAO")
    private String description;

    @CsvCustomBindByName(column = "STATUS", converter = BooleanToStatusCsvConverter.class, required = true)
    private boolean active;

    @CsvBindByName(column = "DATA CADASTRO", required = true)
    @CsvDate("dd/MM/yyyy")
    private LocalDateTime registerDate;

    @CsvBindByName(column = "OPERADOR CADASTRO", required = true)
    private Long registerOperator;

    @CsvBindByName(column = "DATA ATUALIZACAO")
    @CsvDate("dd/MM/yyyy")
    private LocalDateTime updateDate;

    @CsvBindByName(column = "OPERADOR ATUALIZACAO")
    private Long updateOperator;
}