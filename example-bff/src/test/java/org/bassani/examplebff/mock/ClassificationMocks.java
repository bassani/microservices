package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Slf4j
public class ClassificationMocks {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<ClassificationResponse> mockedClassificationTypes(){
        return List.of(
        		ClassificationResponse.builder().id(1L).name("BLACK FRIDAY").active("habilitado").build(),
                ClassificationResponse.builder().id(2L).name("BONIFICACAO").active("habilitado").build(),
                ClassificationResponse.builder().id(3L).name("COLABORATIVA").active("habilitado").build(),
                ClassificationResponse.builder().id(4L).name("COVID").active("habilitado").build(),
                ClassificationResponse.builder().id(5L).name("E COMMERCE").active("habilitado").build(),
                ClassificationResponse.builder().id(6L).name("LANCAMENTO").active("habilitado").build(),
                ClassificationResponse.builder().id(7L).name("MARCA PROPRIA").active("habilitado").build(),
                ClassificationResponse.builder().id(8L).name("NEGOCIAÇAO").active("habilitado").build(),
                ClassificationResponse.builder().id(9L).name("NEGOCIAÇAO - COLABORATIVA").active("habilitado").build(),
                ClassificationResponse.builder().id(10L).name("PACKS").active("habilitado").build(),
                ClassificationResponse.builder().id(11L).name("PBM").active("habilitado").build(),
                ClassificationResponse.builder().id(12L).name("PRE ALTA").active("habilitado").build(),
                ClassificationResponse.builder().id(13L).name("PROGRAMAÇAO").active("habilitado").build(),
                ClassificationResponse.builder().id(14L).name("PROMOCIONAIS").active("habilitado").build(),
                ClassificationResponse.builder().id(15L).name("CAMPANHAS").active("habilitado").build(),
                ClassificationResponse.builder().id(16L).name("REFORÇO").active("habilitado").build(),
                ClassificationResponse.builder().id(17L).name("REGULAR").active("habilitado").build(),
                ClassificationResponse.builder().id(18L).name("REGULAR  ANTECIPAÇAO").active("habilitado").build(),
                ClassificationResponse.builder().id(19L).name("REGULAR  COTA").active("habilitado").build(),
                ClassificationResponse.builder().id(20L).name("SAZONAL INVERNO").active("habilitado").build(),
                ClassificationResponse.builder().id(21L).name("SAZONAL VERAO").active("habilitado").build(),
                ClassificationResponse.builder().id(22L).name("VMI").active("habilitado").build()
        );
    }
    
    public static ClassificationResponse getOrder(int id) {
   	 return mockedClassificationTypes().stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
   		 
   	}

    public static String mockedClassificationAsJson(List list, Pageable pageable) {
        return safeWriteValueAsString(new PageImpl<>(list, pageable, list.size()));
    }

    public static String safeWriteValueAsString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }

    public static String mockedCsvAsString() {
        return mockedCsvHeaderAsString() + "BLACK FRIDAY,Black Friday,HABILITADO,18/05/2021,Operador 1," +
                "19/05/2021,Operador 1\n";
    }

    public static String mockedCsvHeaderAsString() {
        return "NOME CLASSIFICACAO,DESCRICAO CLASSIFICACAO,STATUS,DATA CADASTRO,OPERADOR CADASTRO,DATA ATUALIZACAO," +
                "OPERADOR ATUALIZACAO\n";
    }

    private static Resource csvAsStringToResource(String csv) {
        return new ByteArrayResource(csv.getBytes(StandardCharsets.UTF_8));
    }

    public static Resource mockedCsvAsResource() {
        return csvAsStringToResource(mockedCsvAsString());
    }

    public static Resource mockedCsvHeaderAsResource() {
        return csvAsStringToResource(mockedCsvHeaderAsString());
    }
}
