package com.payroll.util;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public final class Web {
    private Web() {}

    public static List<Map<String, Object>> rows(ResultSet rs) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int count = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= count; i++) {
                row.put(md.getColumnLabel(i).toLowerCase(Locale.ROOT), rs.getObject(i));
            }
            result.add(row);
        }
        return result;
    }

    public static String value(HttpServletRequest request, String name) {
        String v = request.getParameter(name);
        return v == null ? "" : v.trim();
    }

    public static int intValue(HttpServletRequest request, String name) {
        return Integer.parseInt(value(request, name));
    }

    public static BigDecimal decimal(HttpServletRequest request, String name) {
        String v = value(request, name);
        return v.isEmpty() ? BigDecimal.ZERO : new BigDecimal(v);
    }

    public static void flash(HttpServletRequest request, String message, boolean error) {
        request.setAttribute("message", message);
        request.setAttribute("error", error);
    }
}
