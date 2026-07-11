<%@ page import="java.util.List" %>
<%@ page import="model.Material" %>
<!DOCTYPE html>
<html>
<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/footer.jsp" %>
<body>

    <form action="${pageContext.request.contextPath}/MaterialServlet" method="GET" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search materials..." value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn" style="background-color: #6b7280; margin-top: 15px;">Clear</a>
    </form>

<a href="${pageContext.request.contextPath}/jsp/addMaterial.jsp">Add New Material</a>
<br><br>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Quantity</th>
        <th>Reorder Level</th>
        <th>Actions</th>
    </tr>

    <%
        List<Material> list = (List<Material>) request.getAttribute("materialsList");
        if (list == null || list.isEmpty()) {
    %>
    <tr>
        <td colspan="5">No materials found in inventory.</td>
    </tr>
    <% } else {
        for (Material m : list) {
    %>
    <tr>
        <td><%= m.getId() %></td>
        <td><%= m.getName() %></td>
        <td><%= m.getQuantity() %></td>
        <td><%= m.getReorderLevel() %></td>
        <td>
            <a href="${pageContext.request.contextPath}/MaterialServlet?action=edit&id=<%= m.getId() %>">Edit</a> |
            <a href="${pageContext.request.contextPath}/MaterialServlet?action=delete&id=<%= m.getId() %>">Delete</a>
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
