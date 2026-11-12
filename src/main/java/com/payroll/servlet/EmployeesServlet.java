package com.payroll.servlet;

import com.payroll.util.DB;
import com.payroll.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT EMPLOYEE_ID, EMPLOYEE_CODE, FIRST_NAME, LAST_NAME, EMAIL, PHONE, JOB_TITLE, " +
                    "HIRE_DATE, DEPARTMENT_ID, BASIC_SALARY, STATUS FROM EMPLOYEE ORDER BY EMPLOYEE_ID");
                 ResultSet rs = ps.executeQuery()) {
                request.setAttribute("employees", Web.rows(rs));
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT DEPARTMENT_ID, DEPARTMENT_NAME FROM DEPARTMENT ORDER BY DEPARTMENT_NAME");
                 ResultSet rs = ps.executeQuery()) {
                request.setAttribute("departments", Web.rows(rs));
            }
            request.getRequestDispatcher("/WEB-INF/views/employees.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load employees.", e);
        }
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try (Connection con = DB.getConnection();
             CallableStatement cs = con.prepareCall("{call PR_ADD_EMPLOYEE(?,?,?,?,?,?,?,?)}")) {
            cs.setString(1, Web.value(request, "employeeCode"));
            cs.setString(2, Web.value(request, "firstName"));
            cs.setString(3, Web.value(request, "lastName"));
            cs.setString(4, Web.value(request, "email"));
            cs.setString(5, Web.value(request, "phone"));
            cs.setString(6, Web.value(request, "jobTitle"));
            cs.setInt(7, Web.intValue(request, "departmentId"));
            cs.setBigDecimal(8, Web.decimal(request, "basicSalary"));
            cs.execute();
            con.commit();
            response.sendRedirect(request.getContextPath() + "/employees?success=added");
        } catch (Exception e) {
            request.setAttribute("formError", friendly(e));
            doGet(request, response);
        }
    }

    private String friendly(Exception e) {
        String m = e.getMessage();
        if (m == null) return "Could not add employee. Check the entered details.";
        if (m.contains("20010")) return "Employee code or email already exists.";
        if (m.contains("20011")) return "Please select a valid department.";
        return "Could not add employee: " + m;
    }
}
