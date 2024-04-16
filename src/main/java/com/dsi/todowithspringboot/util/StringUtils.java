package com.dsi.todowithspringboot.util;

import jakarta.servlet.http.HttpServletRequest;

public class StringUtils {
    public static String getQueryParams(HttpServletRequest request) {
        StringBuilder queryParams = new StringBuilder();
        request.getParameterMap().forEach((key, values) -> {
            for (String value : values) {
                if (!queryParams.isEmpty()) {
                    queryParams.append("&");
                }
                queryParams.append(key).append("=").append(value);
            }
        });

        return queryParams.toString();
    }
}
