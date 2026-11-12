package com.payroll.servlet;

import com.payroll.util.DB;
import com.payroll.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/payroll")
public class PayrollServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT EMPLOYEE_ID, EMPLOYEE_CODE, FIRST_NAME || ' ' || LAST_NAME EMPLOYEE_NAME " +
                    "FROM EMPLOYEE WHERE STATUS='ACTIVE' ORDER BY EMPLOYEE_CODE");
                 ResultSet rs = ps.executeQuery()) {
                request.setAttribute("employees", Web.rows(rs));
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT P.PAYROLL_ID,E.EMPLOYEE_CODE,E.FIRST_NAME || ' ' || E.LAST_NAME EMPLOYEE_NAME, " +
                    "P.PAYROLL_MONTH,P.BASIC_SALARY,P.BONUS,P.OVERTIME_PAY,P.DEDUCTIONS,P.NET_SALARY,P.PAYROLL_STATUS " +
                    "FROM PAYROLL P JOIN EMPLOYEE E ON E.EMPLOYEE_ID=P.EMPLOYEE_ID " +
                    "ORDER BY P.PAYROLL_MONTH DESC,P.PAYROLL_ID DESC")) {
                ps.setMaxRows(150);
                try (ResultSet rs = ps.executeQuery()) {
                    request.setAttribute("payrollRows", Web.rows(rs));
                }
            }
            request.getRequestDispatcher("/WEB-INF/views/payroll.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load payroll.", e);
        }
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try (Connection con = DB.getConnection();
             CallableStatement cs = con.prepareCall("{call PR_GENERATE_PAYROLL(?,?,?,?,?)}")) {
            cs.setInt(1, Web.intValue(request, "employeeId"));
            cs.setDate(2, Date.valueOf(Web.value(request, "payrollMonth") + "-01"));
            cs.setBigDecimal(3, Web.decimal(request, "bonus"));
            cs.setBigDecimal(4, Web.decimal(request, "overtimePay"));
            cs.setBigDecimal(5, Web.decimal(request, "deductions"));
            cs.execute();
            con.commit();
            response.sendRedirect(request.getContextPath() + "/payroll?success=generated");
        } catch (Exception e) {
            request.setAttribute("formError", "Could not generate payroll. " + e.getMessage());
            doGet(request, response);
        }
    }
}
