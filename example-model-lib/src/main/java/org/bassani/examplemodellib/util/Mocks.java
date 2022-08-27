package org.bassani.examplemodellib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe utilitária para geração de mocks.
 */
@Slf4j
public class Mocks {

    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.unsorted());
    public static final Random RANDOM = new Random();
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(
                new Jdk8Module()).registerModule(new JavaTimeModule());
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    private Mocks() {
        throw new AssertionError();
    }

    /**
     * Extrai de objetos Response nomes de variável que serão serializados em chaves JSON.
     *
     * @param response Objeto Response.
     * @return Vetor com os as chaves JSON.
     */
    public static String[] jsonKeysFrom(Class<?> response) {
        return Arrays.stream(response.getDeclaredFields()).map(Field::getName).filter(
                field -> !field.equals("serialVersionUID")).toArray(String[]::new);
    }

    /**
     * Converte objeto em JSON.
     *
     * @param value JSON como String.
     * @return
     */
    public static String asJson(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }


    /**
     * Cria lista de {@code Long} com elementos variando entre minInclusive e maxExclusive (não incluindo) e com
     * tamanho variável entre 1 e maxSize elementos.
     *
     * @param maxSize      Tamanho máximo da lista
     * @param minInclusive Número mínimo, inclusive.
     * @param maxExclusive Número máximo
     * @return
     */
    public static List<Long> variableSizedList(int maxSize, long minInclusive, long maxExclusive) {
        return RANDOM.longs(RANDOM.nextInt(maxSize) + 1L, minInclusive, maxExclusive).boxed().collect(
                Collectors.toList());
    }

    public static Set<Long> variableSizedSet(int maxSize, long minInclusive, long maxExclusive) {
        return RANDOM.longs(RANDOM.nextInt(maxSize) + 1L, minInclusive, maxExclusive).boxed().collect(
                Collectors.toSet());
    }

    /** Gera int aleatório entre minInclusive e maxExclusive (não incluindo).
     * @param minInclusive mínimo
     * @param maxExclusive máximo + 1
     * @return
     */
    public static int intBetween(int minInclusive, int maxExclusive) {
        return RANDOM.ints(1, minInclusive, maxExclusive).sum();
    }

    public static Long longBetween(long minInclusive, long maxExclusive) {
        return RANDOM.longs(1, minInclusive, maxExclusive).sum();
    }
}
