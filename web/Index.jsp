<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Inventory System</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="jsp/includes/Header.jsp" %>
<%@ include file="jsp/includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Dashboard Overview</h2>

    <div class="card-container">
        <div class="card">
            <h3>Materials</h3>
            <p>Inventory</p>
            <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn">Manage Materials</a>
        </div>

        <div class="card">
            <h3>Suppliers</h3>
            <p>Contacts</p>
            <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn">View Suppliers</a>
        </div>

        <div class="card">
            <h3>Cleaners</h3>
            <p>Staff Roster</p>
            <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn">Manage Staff</a>
        </div>

        <div class="card">
            <h3>Stock Issuance</h3>
            <p>Operations</p>
            <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list" class="btn">View Issuances</a>
        </div>
    </div>
</div>

<%@ include file="jsp/includes/footer.jsp" %>

</body>
</html>