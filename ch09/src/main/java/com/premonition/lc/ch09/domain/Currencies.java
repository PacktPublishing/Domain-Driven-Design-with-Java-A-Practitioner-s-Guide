package com.premonition.lc.ch09.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Currencies {
    private static final Comparator<Currency> comparator = (o1, o2) ->
            String.CASE_INSENSITIVE_ORDER.compare(o1.getCurrencyCode(), o2.getCurrencyCode());
    private static final String[] countries = {"USD", "INR", "AUD", "CAD", "SBD", "EUR"};

    private Currencies() {
    }

    public static Set<Currency> tradeable() {
        return Arrays.stream(countries)
                .map(Currency::getInstance)
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator)));
    }
}
