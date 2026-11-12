<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,java.time.*,java.time.format.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= request.getAttribute("pageTitle") == null ? "Employee Payroll System" : request.getAttribute("pageTitle") %></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<header class="topbar">
  <div class="brand"><span class="brand-mark">EP</span><div><strong>Employee Payroll</strong><small>Attendance & Management System</small></div></div>
  <span class="top-note">Oracle Database · PL/SQL</span>
</header>
<div class="layout">
<aside class="sidebar">
  <div class="nav-label">MAIN MENU</div>
  <a href="<%=request.getContextPath()%>/dashboard">▦ &nbsp; Dashboard</a>
  <a href="<%=request.getContextPath()%>/employees">♙ &nbsp; Employees</a>
  <a href="<%=request.getContextPath()%>/departments">▤ &nbsp; Departments</a>
  <a href="<%=request.getContextPath()%>/attendance">◷ &nbsp; Attendance</a>
  <a href="<%=request.getContextPath()%>/payroll">₹ &nbsp; Payroll</a>
  <a href="<%=request.getContextPath()%>/reports">▥ &nbsp; Reports</a>
  <div class="sidebar-foot">Local administrator workspace</div>
</aside>
<main class="content">
