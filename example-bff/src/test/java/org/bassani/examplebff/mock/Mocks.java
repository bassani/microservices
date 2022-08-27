package org.bassani.examplebff.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Mocks {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int FIRST_PAGE = 0;
    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(FIRST_PAGE, DEFAULT_PAGE_SIZE);
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    private Mocks() {
        throw new AssertionError();
    }

    static boolean isLastPage(int pageNumber, int totalPages) {
        return pageNumber + 1 == totalPages;
    }

    static long getLastIndex(Pageable pageable, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / (double) pageable.getPageSize());
        if (isLastPage(pageable.getPageNumber(), totalPages))
            return totalElements;
        return pageable.getOffset() + pageable.getPageSize();
    }

    public static String[] jsonKeysFrom(Class<?> response) {
        return Arrays.stream(response.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    private static <T> List<T> generateResponses(IntFunction<T> generator, int total) {
        return IntStream.iterate(0, num -> num + 1).mapToObj(generator).limit(total).collect(Collectors.toList());
    }

    public static <T> Page<T> mockedResponses(Pageable pageable, int total, IntFunction<T> generator) {
        int first = (int) pageable.getOffset();
        int last = (int) getLastIndex(pageable, total);

        List<T> responses = generateResponses(generator, total).subList(first, last);
        return new PageImpl<>(responses, pageable, total);
    }

    public static String safeWriteValueAsJsonString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }
}
