<%@ page import="java.util.List" %>
<%@ page import="model.Supplier" %>
<!DOCTYPE html>
<html>
<head>
    <title>Suppliers Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Suppliers Management</h2>

    <form action="${pageContext.request.contextPath}/SupplierServlet" method="GET" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search suppliers..."
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn" style="background-color: #6b7280; margin-top: 15px;">Clear</a>
    </form>

    <a href="${pageContext.request.contextPath}/SupplierServlet?action=add" class="btn btn-success">Add New Supplier</a>
    <br><br>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Contact Person</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Address</th>
            <% if (isSupervisor) { %>
            <th>Actions</th>
            <% }%>
        </tr>
        </thead>
        <tbody>
        <%
            List<Supplier> list = (List<Supplier>) request.getAttribute("suppliersList");
            if (list == null || list.isEmpty()) {
        %>
        <tr>
            <td colspan="7">No suppliers found.</td>
        </tr>
        <% } else {
            for (Supplier s : list) {
        %>
        <tr>
            <td><%= s.getSupplierId() %></td>
            <td><%= s.getSupplierName() %></td>
            <td><%= s.getContactPerson() %></td>
            <td><%= s.getPhone() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getAddress() %></td>
            <% if (isSupervisor) { %>
            <td>
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=edit&id=<%= s.getSupplierId() %>" class="btn" style="padding: 5px 10px; margin: 0;">Edit</a>
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=delete&id=<%= s.getSupplierId() %>" class="btn btn-danger" style="padding: 5px 10px; margin: 0;" onclick="return confirm('Delete this supplier?')">Delete</a>
            </td>
            <% } %>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>