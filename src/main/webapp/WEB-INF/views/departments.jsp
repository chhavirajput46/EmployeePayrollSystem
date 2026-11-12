<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">ORGANIZATION</p><h1>Departments</h1><p class="muted">Departments currently registered in Oracle.</p></div></div>
<section class="panel"><div class="table-wrap"><table><thead><tr><th>Department ID</th><th>Department name</th><th>Description</th><th>Employees</th></tr></thead><tbody>
<% List<Map<String,Object>> ds=(List<Map<String,Object>>)request.getAttribute("departments"); for(Map<String,Object>d:ds){ %>
<tr><td><%=d.get("department_id")%></td><td><b><%=d.get("department_name")%></b></td><td><%=d.get("description")==null?"—":d.get("description")%></td><td><span class="pill"><%=d.get("total_employees")%></span></td></tr>
<% } %>
</tbody></table></div></section>
<div class="hint">Department records are displayed from the existing DEPARTMENT table. No SQL setup script is included in this download.</div>
<%@ include file="_footer.jsp" %>
