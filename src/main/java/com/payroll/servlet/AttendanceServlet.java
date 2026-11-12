package com.payroll.servlet;

import com.payroll.util.DB;
import com.payroll.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {
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
                    "SELECT A.ATTENDANCE_ID, E.EMPLOYEE_CODE, E.FIRST_NAME || ' ' || E.LAST_NAME EMPLOYEE_NAME, " +
                    "A.ATTENDANCE_DATE, A.ATTENDANCE_STATUS, A.OVERTIME_HOURS, A.REMARKS " +
                    "FROM ATTENDANCE A JOIN EMPLOYEE E ON E.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                    "ORDER BY A.ATTENDANCE_DATE DESC, A.ATTENDANCE_ID DESC")) {
                ps.setMaxRows(150);
                try (ResultSet rs = ps.executeQuery()) {
                    request.setAttribute("attendanceRows", Web.rows(rs));
                }
            }
            request.getRequestDispatcher("/WEB-INF/views/attendance.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load attendance.", e);
        }
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try (Connection con = DB.getConnection();
             CallableStatement cs = con.prepareCall("{call PR_MARK_ATTENDANCE(?,?,?,?,?)}")) {
            cs.setInt(1, Web.intValue(request, "employeeId"));
            cs.setDate(2, Date.valueOf(Web.value(request, "attendanceDate")));
            cs.setString(3, Web.value(request, "status"));
            cs.setBigDecimal(4, Web.decimal(request, "overtimeHours"));
            String remarks = Web.value(request, "remarks");
            if (remarks.isEmpty()) cs.setNull(5, Types.VARCHAR); else cs.setString(5, remarks);
            cs.execute();
            con.commit();
            response.sendRedirect(request.getContextPath() + "/attendance?success=marked");
        } catch (Exception e) {
            request.setAttribute("formError", "Could not mark attendance. " + e.getMessage());
            doGet(request, response);
        }
    }
}
