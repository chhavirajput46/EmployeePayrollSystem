<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">PEOPLE</p><h1>Employees</h1><p class="muted">View existing employees and add a new employee.</p></div></div>
<% if ("added".equals(request.getParameter("success"))) { %><div class="alert success">Employee added successfully.</div><% } %>
<% if (request.getAttribute("formError") != null) { %><div class="alert error"><%=request.getAttribute("formError")%></div><% } %>
<section class="panel">
 <h2>Add Employee</h2>
 <form method="post" action="<%=request.getContextPath()%>/employees" class="form-grid">
  <label>Employee code<input name="employeeCode" placeholder="EMP006" required></label>
  <label>First name<input name="firstName" required></label>
  <label>Last name<input name="lastName"></label>
  <label>Email<input type="email" name="email"></label>
  <label>Phone<input name="phone"></label>
  <label>Job title<input name="jobTitle" placeholder="Software Engineer"></label>
  <label>Department<select name="departmentId" required><option value="">Select department</option><% List<Map<String,Object>> ds=(List<Map<String,Object>>)request.getAttribute("departments"); if(ds!=null) for(Map<String,Object>d:ds){ %><option value="<%=d.get("department_id")%>"><%=d.get("department_name")%></option><% } %></select></label>
  <label>Basic salary<input type="number" min="0" step="0.01" name="basicSalary" required></label>
  <div class="form-actions"><button class="btn primary" type="submit">Add employee</button></div>
 </form>
 <p class="hint">This form calls your existing PL/SQL procedure PR_ADD_EMPLOYEE.</p>
</section>
<section class="panel">
 <div class="section-title"><h2>Employee List</h2><span class="count"><%=((List<?>)request.getAttribute("employees")).size()%> records</span></div>
 <div class="table-wrap"><table><thead><tr><th>ID</th><th>Code</th><th>Employee</th><th>Department ID</th><th>Job title</th><th>Salary</th><th>Status</th></tr></thead><tbody>
 <% List<Map<String,Object>> es=(List<Map<String,Object>>)request.getAttribute("employees"); for(Map<String,Object>e:es){ %>
 <tr><td><%=e.get("employee_id")%></td><td><b><%=e.get("employee_code")%></b></td><td><%=e.get("first_name")%> <%=e.get("last_name")==null?"":e.get("last_name")%><br><small><%=e.get("email")==null?"":e.get("email")%></small></td><td><%=e.get("department_id")%></td><td><%=e.get("job_title")==null?"—":e.get("job_title")%></td><td>₹ <%=e.get("basic_salary")%></td><td><span class="pill"><%=e.get("status")%></span></td></tr>
 <% } %>
 </tbody></table></div>
</section>
<%@ include file="_footer.jsp" %>
