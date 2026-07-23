<%@ page import="java.util.List" %>
<%@ page import="model.Material" %>
<%@ page import="model.Cleaner" %>
<%@ page import="model.Supplier" %>
<%@ page import="model.Issuance" %>

<!DOCTYPE html>
<html>

<%@ include file="includes/Header.jsp" %>

<body class="${sessionScope.role == 'Supervisor' ? 'supervisor-theme' : ''}">

<%@ include file="includes/navbar.jsp" %>

<div class="container">

    <h1 class="page-title">Dashboard</h1>

    <div class="card-container">
        <div class="card">
            <h3>Total Materials</h3>
            <p>${totalMaterials}</p>
        </div>
        <div class="card">
            <h3>Total Cleaners</h3>
            <p>${totalCleaners}</p>
        </div>
        <div class="card">
            <h3>Total Suppliers</h3>
            <p>${totalSuppliers}</p>
        </div>
        <div class="card">
            <h3>Low Stock Items</h3>
            <p>${lowStock}</p>
        </div>
    </div>

    <br>

    <h2 class="page-title">Recent Stock Issuances</h2>

    <table>
        <thead>
        <tr>
            <th>Issue ID</th>
            <th>Cleaner</th>
            <th>Material</th>
            <th>Quantity</th>
            <th>Date Issued</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Issuance> issuanceList = (List<Issuance>)request.getAttribute("recentIssuances");
            if(issuanceList != null && !issuanceList.isEmpty()) {
                for(Issuance issue : issuanceList) {
        %>
        <tr>
            <td><%= issue.getId() %></td>
            <td><%= issue.getCleanerName() %></td>
            <td><%= issue.getMaterialName() %></td>
            <td><%= issue.getQuantity() %></td>
            <td><%= issue.getIssuedDate() %></td>
        </tr>
        <%      }
        } else {
        %>
        <tr>
            <td colspan="5" style="text-align: center;">No stock issuances available.</td>
        </tr>
        <%  } %>
        </tbody>
    </table>

    <br><br>

    <h2 class="page-title">Quick Navigation</h2>
    <div>
        <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn">Materials</a>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn">Cleaners</a>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn">Suppliers</a>
        <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list" class="btn">Stock Issuance</a>
        <% if (isSupervisor) { %>
        <a href="${pageContext.request.contextPath}/ReportServlet" class="btn">Reports</a>
        <% } %>
    </div>

</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>