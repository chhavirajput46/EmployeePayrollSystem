package com.payroll.servlet;

import com.payroll.util.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DB.getConnection()) {
            request.setAttribute("employeeCount", count(con, "SELECT COUNT(*) FROM EMPLOYEE"));
            request.setAttribute("departmentCount", count(con, "SELECT COUNT(*) FROM DEPARTMENT"));
            request.setAttribute("attendanceCount", count(con, "SELECT COUNT(*) FROM ATTENDANCE"));
            request.setAttribute("payrollCount", count(con, "SELECT COUNT(*) FROM PAYROLL"));
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load dashboard. Check Oracle connection.", e);
        }
    }

    private int count(Connection con, String sql) throws SQLException {
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
