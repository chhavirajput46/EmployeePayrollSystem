<%@ include file="_header.jsp" %>
<div class="page-heading"><div><p class="eyebrow">OVERVIEW</p><h1>Dashboard</h1><p class="muted">A quick summary of your employee and payroll database.</p></div></div>
<div class="stats">
  <div class="stat-card"><span class="stat-icon blue">♙</span><span class="stat-label">Employees</span><strong><%=request.getAttribute("employeeCount")%></strong></div>
  <div class="stat-card"><span class="stat-icon purple">▤</span><span class="stat-label">Departments</span><strong><%=request.getAttribute("departmentCount")%></strong></div>
  <div class="stat-card"><span class="stat-icon orange">◷</span><span class="stat-label">Attendance Records</span><strong><%=request.getAttribute("attendanceCount")%></strong></div>
  <div class="stat-card"><span class="stat-icon green">₹</span><span class="stat-label">Payroll Records</span><strong><%=request.getAttribute("payrollCount")%></strong></div>
</div>
<section class="panel welcome">
 <div><p class="eyebrow">SYSTEM READY</p><h2>Manage your workforce in one place</h2><p class="muted">Use the menu to manage employees, record daily attendance, generate payroll and view reports.</p></div>
 <div class="welcome-art">EP</div>
</section>
<div class="quick-grid">
 <a class="quick-card" href="<%=request.getContextPath()%>/employees"><b>＋ Add employee</b><span>Create an employee record</span></a>
 <a class="quick-card" href="<%=request.getContextPath()%>/attendance"><b>◷ Mark attendance</b><span>Record present, absent or leave</span></a>
 <a class="quick-card" href="<%=request.getContextPath()%>/payroll"><b>₹ Generate payroll</b><span>Calculate monthly net salary</span></a>
</div>
<%@ include file="_footer.jsp" %>
