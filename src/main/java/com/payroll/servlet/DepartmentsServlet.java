package com.payroll.servlet;

import com.payroll.util.DB;
import com.payroll.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/departments")
public class DepartmentsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT D.DEPARTMENT_ID, D.DEPARTMENT_NAME, D.DESCRIPTION, COUNT(E.EMPLOYEE_ID) TOTAL_EMPLOYEES " +
                     "FROM DEPARTMENT D LEFT JOIN EMPLOYEE E ON E.DEPARTMENT_ID=D.DEPARTMENT_ID " +
                     "GROUP BY D.DEPARTMENT_ID,D.DEPARTMENT_NAME,D.DESCRIPTION ORDER BY D.DEPARTMENT_ID");
             ResultSet rs = ps.executeQuery()) {
            request.setAttribute("departments", Web.rows(rs));
            request.getRequestDispatcher("/WEB-INF/views/departments.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load departments.", e);
        }
    }
}
