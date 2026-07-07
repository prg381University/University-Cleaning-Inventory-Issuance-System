<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav>
    <a href="dashboard.jsp">Dashboard</a>
    <a href="materials.jsp">Materials</a>
    <a href="suppliers.jsp">Suppliers</a>
    <a href="cleaners.jsp">Cleaners</a>
    <a href="issuance.jsp">Stock Issuance</a>
    <a href="reports.jsp">Reports</a>


<%-- todo Jordan --%>

    <span class="nav-right">
        <span class="user-role">
            Welcome, ${sessionScope.username}
        </span>

        <a href="../logout" class="logout-btn">Logout</a>
    </span>
</nav>