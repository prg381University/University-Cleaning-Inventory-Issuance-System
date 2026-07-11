<%@ page import="java.util.List" %>
<%@ page import="model.Material" %>
<!DOCTYPE html>
<html>
<head>
    <title>Materials Inventory</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Materials Inventory</h2>

    <form action="${pageContext.request.contextPath}/MaterialServlet" method="GET" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search materials..." value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn" style="background-color: #6b7280; margin-top: 15px;">Clear</a>
    </form>

    <a href="${pageContext.request.contextPath}/jsp/addMaterial.jsp" class="btn btn-success">Add New Material</a>
    <br><br>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>Reorder Level</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Material> list = (List<Material>) request.getAttribute("materialsList");
            if (list == null || list.isEmpty()) {
        %>
        <tr>
            <td colspan="5">No materials found in inventory.</td>
        </tr>
        <% } else {
            for (Material m : list) {
                boolean isLowStock = m.getQuantity() <= m.getReorderLevel();
        %>
        <tr class="<%= isLowStock ? "warning" : "" %>">
            <td><%= m.getId() %></td>
            <td>
                <%= m.getName() %>
                <%= isLowStock ? "(Low Stock)" : "" %>
            </td>
            <td><%= m.getQuantity() %></td>
            <td><%= m.getReorderLevel() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/MaterialServlet?action=edit&id=<%= m.getId() %>" class="btn" style="padding: 5px 10px; margin: 0;">Edit</a>
                <a href="${pageContext.request.contextPath}/MaterialServlet?action=delete&id=<%= m.getId() %>" class="btn btn-danger" style="padding: 5px 10px; margin: 0;">Delete</a>
            </td>
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