package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class OrderTypes {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static Stream<OrderTypeResponse> generateOrderType(int total, Predicate<OrderTypeResponse> containing) {
        final int alphabetSize = 26;
        final int firstCapitalLetter = 65;
        return LongStream.iterate(0, num -> num + 1)
                .mapToObj(i -> new OrderTypeResponse(i, i + "" + (char) (i % alphabetSize + firstCapitalLetter)))
                .filter(containing)
                .limit(total);
    }

    private static boolean isLastPage(int pageNumber, int totalPages) {
        return pageNumber + 1 == totalPages;
    }

    private static long getLastIndex(Pageable pageable, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / (double) pageable.getPageSize());
        if (isLastPage(pageable.getPageNumber(), totalPages))
            return totalElements;
        return pageable.getOffset() + pageable.getPageSize();
    }

    private static Predicate<OrderTypeResponse> containingQueryFrom(OrderTypeRequestParams request) {
        Predicate<OrderTypeResponse> predicate = orderType -> true;
        if (request != null) {
            if (request.getQuery() != null) {
                predicate = orderType -> String.valueOf(orderType.getId()).contains(request.getQuery()) || orderType.getName().contains(request.getQuery());
            } else if (request.getId() != null) {
                predicate = orderType -> String.valueOf(orderType.getId()).contains(String.valueOf(request.getId()));
            } else if (request.getName() != null) {
                predicate = orderType -> orderType.getName().contains(request.getName());
            }
        }
        return predicate;
    }

    private static List<OrderTypeResponse> generateOrderTypes(int total, int first, int last,
                                                              OrderTypeRequestParams request) {
        List<OrderTypeResponse> orderTypeResponses = generateOrderType(total, containingQueryFrom(request)).collect(toList());

        if (orderTypeResponses.size() < first || orderTypeResponses.size() < last) {
            return orderTypeResponses;
        } else {
            return orderTypeResponses.subList(first, last);
        }
    }

    public static Page<OrderTypeResponse> mockedOrderTypes(OrderTypeRequestParams request, Pageable pageable,
                                                           int total) {
        int first = (int) pageable.getOffset();
        int last = (int) getLastIndex(pageable, total);

        List<OrderTypeResponse> allOrderTypes = generateOrderTypes(total, first, last, request);
        return new PageImpl<>(allOrderTypes, pageable, total);
    }

    public static Page<OrderTypeResponse> mockedOrderTypes(Pageable pageable, int total) {
        return mockedOrderTypes(null, pageable, total);
    }

    private static String safeWriteValueAsString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }

    public static String mockedOrderTypesAsJson(Pageable pageable, int total) {
        return safeWriteValueAsString(mockedOrderTypes(pageable, total));
    }

    public static String mockedOrderTypesAsJson(OrderTypeRequestParams request, Pageable pageable, int total) {
        return safeWriteValueAsString(mockedOrderTypes(request, pageable, total));
    }
}
