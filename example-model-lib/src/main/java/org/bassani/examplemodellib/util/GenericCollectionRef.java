package org.bassani.examplemodellib.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GenericCollectionRef {
    private Long id;
    private String name;
}
