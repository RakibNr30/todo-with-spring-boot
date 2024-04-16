package com.dsi.todowithspringboot.util;

import org.springframework.data.domain.Sort;

public class SortUtils {
    public static Sort latest() {
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }

    public static Sort oldest() {
        return Sort.by(Sort.Direction.ASC, "createdAt");
    }
}
