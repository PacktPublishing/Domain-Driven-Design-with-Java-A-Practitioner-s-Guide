package com.premonition.lc.ch06.domain;

import org.springframework.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TenorType {
    AT_SIGHT, DEFERRED;

    @Override
    public String toString() {
        final String[] words = name().toLowerCase().split("_");
        return Stream.of(words)
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }
}
