<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    boolean isSupervisor = user != null && "SUPERVISOR".equals(user.getRole());

    String currentPath = request.getRequestURI();
    String queryString = request.getQueryString();
    String fullPath = currentPath + (queryString != null ? "?" + queryString : "");
%>

<nav>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/HomeServlet">Dashboard</a>
        <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="<%= fullPath.contains("MaterialServlet") ? "active" : "" %>">Materials</a>
        <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list" class="<%= fullPath.contains("IssuanceServlet") ? "active" : "" %>">Stock Issuance</a>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="<%= fullPath.contains("SupplierServlet") ? "active" : "" %>">Suppliers</a>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="<%= fullPath.contains("CleanerServlet") ? "active" : "" %>">Cleaners</a>

        <% if (isSupervisor) { %>
        <a href="${pageContext.request.contextPath}/ReportServlet">Reports</a>
        <% } %>
    </div>

    <div class="nav-right">
        <% if (user != null) {
            String badgeClass = "SUPERVISOR".equals(user.getRole()) ? "role-supervisor" : "role-storekeeper";
        %>
        <div class="dropdown">
            <button class="dropbtn">
                <%= user.getUsername() %>
                <span class="role-badge <%= badgeClass %>"><%= user.getRole() %></span>
                &#9660;
            </button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/logout" onclick="return confirm('Are you sure you want to log out?');">Logout</a>
            </div>
        </div>
        <% } else { %>
        <a href="${pageContext.request.contextPath}/login">Login</a>
        <% } %>
    </div>
</nav>