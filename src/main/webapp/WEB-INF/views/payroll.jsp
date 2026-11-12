<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">SALARY MANAGEMENT</p><h1>Payroll</h1><p class="muted">Generate a monthly payroll record for an employee.</p></div></div>
<% if ("generated".equals(request.getParameter("success"))) { %><div class="alert success">Payroll generated successfully.</div><% } %>
<% if (request.getAttribute("formError") != null) { %><div class="alert error"><%=request.getAttribute("formError")%></div><% } %>
<section class="panel"><h2>Generate Payroll</h2>
<form method="post" action="<%=request.getContextPath()%>/payroll" class="form-grid">
<label>Employee<select name="employeeId" required><option value="">Select employee</option><% List<Map<String,Object>> es=(List<Map<String,Object>>)request.getAttribute("employees"); for(Map<String,Object>e:es){ %><option value="<%=e.get("employee_id")%>"><%=e.get("employee_code")%> — <%=e.get("employee_name")%></option><% } %></select></label>
<label>Payroll month<input type="month" name="payrollMonth" required></label>
<label>Bonus (₹)<input type="number" name="bonus" min="0" step="0.01" value="0"></label>
<label>Overtime pay (₹)<input type="number" name="overtimePay" min="0" step="0.01" value="0"></label>
<label>Deductions (₹)<input type="number" name="deductions" min="0" step="0.01" value="0"></label>
<div class="form-actions"><button class="btn primary" type="submit">Generate payroll</button></div>
</form><p class="hint">Net salary is calculated by your existing FN_CALCULATE_NET_SALARY function through PR_GENERATE_PAYROLL.</p></section>
<section class="panel"><div class="section-title"><h2>Payroll Records</h2><span class="count">Latest 150 records</span></div>
<div class="table-wrap"><table><thead><tr><th>ID</th><th>Employee</th><th>Month</th><th>Basic</th><th>Bonus</th><th>Overtime</th><th>Deductions</th><th>Net salary</th><th>Status</th></tr></thead><tbody>
<% List<Map<String,Object>> pr=(List<Map<String,Object>>)request.getAttribute("payrollRows"); for(Map<String,Object>p:pr){ %>
<tr><td><%=p.get("payroll_id")%></td><td><b><%=p.get("employee_code")%></b><br><%=p.get("employee_name")%></td><td><%=p.get("payroll_month")%></td><td>₹ <%=p.get("basic_salary")%></td><td>₹ <%=p.get("bonus")%></td><td>₹ <%=p.get("overtime_pay")%></td><td>₹ <%=p.get("deductions")%></td><td><b>₹ <%=p.get("net_salary")%></b></td><td><span class="pill"><%=p.get("payroll_status")%></span></td></tr>
<% } %></tbody></table></div></section>
<%@ include file="_footer.jsp" %>
