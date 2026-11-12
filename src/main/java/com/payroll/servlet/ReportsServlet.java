package com.payroll.servlet;

import com.payroll.util.DB;
import com.payroll.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reports")
public class ReportsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT D.DEPARTMENT_NAME, COUNT(E.EMPLOYEE_ID) TOTAL_EMPLOYEES, " +
                    "NVL(SUM(E.BASIC_SALARY),0) TOTAL_BASIC_SALARY " +
                    "FROM DEPARTMENT D LEFT JOIN EMPLOYEE E ON E.DEPARTMENT_ID=D.DEPARTMENT_ID " +
                    "GROUP BY D.DEPARTMENT_NAME ORDER BY D.DEPARTMENT_NAME");
                 ResultSet rs = ps.executeQuery()) {
                request.setAttribute("departmentReport", Web.rows(rs));
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT E.EMPLOYEE_CODE,E.FIRST_NAME || ' ' || E.LAST_NAME EMPLOYEE_NAME, " +
                    "COUNT(A.ATTENDANCE_ID) DAYS_RECORDED, " +
                    "SUM(CASE WHEN A.ATTENDANCE_STATUS='PRESENT' THEN 1 ELSE 0 END) PRESENT_DAYS, " +
                    "SUM(CASE WHEN A.ATTENDANCE_STATUS='ABSENT' THEN 1 ELSE 0 END) ABSENT_DAYS " +
                    "FROM EMPLOYEE E LEFT JOIN ATTENDANCE A ON A.EMPLOYEE_ID=E.EMPLOYEE_ID " +
                    "GROUP BY E.EMPLOYEE_CODE,E.FIRST_NAME,E.LAST_NAME ORDER BY E.EMPLOYEE_CODE");
                 ResultSet rs = ps.executeQuery()) {
                request.setAttribute("attendanceReport", Web.rows(rs));
            }
            request.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load reports.", e);
        }
    }
}
