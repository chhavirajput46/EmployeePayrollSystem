<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">INSIGHTS</p><h1>Reports</h1><p class="muted">Department summary and employee attendance overview.</p></div></div>
<section class="panel"><h2>Department Summary</h2><div class="table-wrap"><table><thead><tr><th>Department</th><th>Total employees</th><th>Total basic salary</th></tr></thead><tbody>
<% List<Map<String,Object>> dr=(List<Map<String,Object>>)request.getAttribute("departmentReport"); for(Map<String,Object>r:dr){ %><tr><td><b><%=r.get("department_name")%></b></td><td><%=r.get("total_employees")%></td><td>₹ <%=r.get("total_basic_salary")%></td></tr><% } %>
</tbody></table></div></section>
<section class="panel"><h2>Attendance Summary</h2><div class="table-wrap"><table><thead><tr><th>Code</th><th>Employee</th><th>Days recorded</th><th>Present</th><th>Absent</th></tr></thead><tbody>
<% List<Map<String,Object>> ar=(List<Map<String,Object>>)request.getAttribute("attendanceReport"); for(Map<String,Object>r:ar){ %><tr><td><b><%=r.get("employee_code")%></b></td><td><%=r.get("employee_name")%></td><td><%=r.get("days_recorded")%></td><td><%=r.get("present_days")%></td><td><%=r.get("absent_days")%></td></tr><% } %>
</tbody></table></div></section>
<%@ include file="_footer.jsp" %>
