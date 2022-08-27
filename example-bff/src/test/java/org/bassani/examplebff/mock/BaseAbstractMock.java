package org.bassani.examplebff.mock;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BaseAbstractMock {

    public static final Integer PAGE_ZERO = 0;
    public static final Integer SIZE = 25;

    public static final Sort SORT_BY_NAME = Sort.by("name").ascending();
    public static final Sort SORT_BY_ID = Sort.by("id").ascending();
    public static final Sort SORT_BY_DATE = Sort.by("registerDate").ascending();

    public static final PageRequest PAGE_REQUEST_DEFAULT = PageRequest.of(PAGE_ZERO, SIZE);
    public static final PageRequest PAGE_REQUEST_SORT_BY_NAME = PageRequest.of(PAGE_ZERO, SIZE, SORT_BY_NAME);
    public static final PageRequest PAGE_REQUEST_SORT_BY_ID = PageRequest.of(PAGE_ZERO, SIZE, SORT_BY_ID);
    public static final PageRequest PAGE_REQUEST_SORT_BY_DATE = PageRequest.of(PAGE_ZERO, SIZE, SORT_BY_DATE);

}
