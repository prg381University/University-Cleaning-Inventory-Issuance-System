<%@ page import="java.util.List" %>
<%@ page import="model.Supplier" %>
<!DOCTYPE html>
<html>
<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>
<body>

    <h2>Suppliers Management</h2>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/SupplierServlet" method="GET" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search suppliers..." 
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn" style="background-color: #6b7280; margin-top: 15px;">Clear</a>
    </form>

    <!-- Add New Supplier Link -->
    <a href="${pageContext.request.contextPath}/jsp/addSupplier.jsp">Add New Supplier</a>
    <br><br>

    <!-- Suppliers Table -->
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Contact Person</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>

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
            <td>
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=edit&id=<%= s.getSupplierId() %>">Edit</a> |
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=delete&id=<%= s.getSupplierId() %>" 
                   onclick="return confirm('Delete this supplier?')">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

<%@ include file="includes/footer.jsp" %>
</body>
</html>