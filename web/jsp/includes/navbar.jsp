<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav>
    <a href="dashboard.jsp">Dashboard</a>
    <a href="${pageContext.request.contextPath}/MaterialServlet?action=list">Materials</a>
     <a href="${pageContext.request.contextPath}/SupplierServlet?action=list">Suppliers</a>
     <a href="${pageContext.request.contextPath}/CleanerServlet?action=list">Cleaners</a>
    <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list">Stock Issuance</a>
    <a href="reports.jsp">Reports</a>


<%-- todo Jordan --%>

    <span class="nav-right">
        <span class="user-role">
            Welcome, ${sessionScope.username}
        </span>

        <a href="../logout" class="logout-btn">Logout</a>
    </span>
</nav>