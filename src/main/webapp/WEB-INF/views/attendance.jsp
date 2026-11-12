<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">DAILY OPERATIONS</p><h1>Attendance</h1><p class="muted">Mark attendance using your existing PL/SQL procedure.</p></div></div>
<% if ("marked".equals(request.getParameter("success"))) { %><div class="alert success">Attendance marked successfully.</div><% } %>
<% if (request.getAttribute("formError") != null) { %><div class="alert error"><%=request.getAttribute("formError")%></div><% } %>
<section class="panel"><h2>Mark Attendance</h2>
<form method="post" action="<%=request.getContextPath()%>/attendance" class="form-grid">
<label>Employee<select name="employeeId" required><option value="">Select employee</option><% List<Map<String,Object>> es=(List<Map<String,Object>>)request.getAttribute("employees"); for(Map<String,Object>e:es){ %><option value="<%=e.get("employee_id")%>"><%=e.get("employee_code")%> — <%=e.get("employee_name")%></option><% } %></select></label>
<label>Date<input type="date" name="attendanceDate" required></label>
<label>Status<select name="status" required><option>PRESENT</option><option>ABSENT</option><option>LEAVE</option><option>HALF DAY</option></select></label>
<label>Overtime hours<input type="number" name="overtimeHours" min="0" step="0.25" value="0"></label>
<label class="wide">Remarks<input name="remarks" placeholder="Optional remarks"></label>
<div class="form-actions"><button class="btn primary" type="submit">Save attendance</button></div>
</form><p class="hint">The procedure prevents duplicate attendance for the same employee and date.</p></section>
<section class="panel"><div class="section-title"><h2>Recent Attendance</h2><span class="count">Latest 150 records</span></div>
<div class="table-wrap"><table><thead><tr><th>ID</th><th>Employee</th><th>Date</th><th>Status</th><th>Overtime</th><th>Remarks</th></tr></thead><tbody>
<% List<Map<String,Object>> ar=(List<Map<String,Object>>)request.getAttribute("attendanceRows"); for(Map<String,Object>a:ar){ %>
<tr><td><%=a.get("attendance_id")%></td><td><b><%=a.get("employee_code")%></b><br><%=a.get("employee_name")%></td><td><%=a.get("attendance_date")%></td><td><span class="pill"><%=a.get("attendance_status")%></span></td><td><%=a.get("overtime_hours")%></td><td><%=a.get("remarks")==null?"—":a.get("remarks")%></td></tr>
<% } %></tbody></table></div></section>
<%@ include file="_footer.jsp" %>
