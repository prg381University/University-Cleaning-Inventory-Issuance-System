<%@ page import="java.util.List" %>
<%@ page import="model.Cleaner" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cleaners Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Cleaners Management</h2>

    <form action="${pageContext.request.contextPath}/CleanerServlet" method="GET" style="margin-bottom: 20px;">
        <input type="hidden" name="action" value="search">
        <input type="text" name="query" placeholder="Search cleaners..."
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn" style="background-color: #6b7280; margin-top: 15px;">Clear</a>
    </form>

    <a href="${pageContext.request.contextPath}/CleanerServlet?action=add" class="btn btn-success">Add New Cleaner</a>
    <br><br>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Department</th>
            <th>Phone</th>
            <th>Email</th>
            <% if (isSupervisor) { %>
            <th>Actions</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            List<Cleaner> list = (List<Cleaner>) request.getAttribute("cleanersList");
            if (list == null || list.isEmpty()) {
        %>
        <tr>
            <td colspan="6">No cleaners found.</td>
        </tr>
        <% } else {
            for (Cleaner c : list) {
        %>
        <tr>
            <td><%= c.getId() %></td>
            <td><%= c.getFullName() %></td>
            <td><%= c.getDepartment() %></td>
            <td><%= c.getPhone() %></td>
            <td><%= c.getEmail() %></td>
            <% if (isSupervisor) { %>
            <td>
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=edit&id=<%= c.getId() %>" class="btn" style="padding: 5px 10px; margin: 0;">Edit</a>
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=delete&id=<%= c.getId() %>" class="btn btn-danger" style="padding: 5px 10px; margin: 0;" onclick="return confirm('Delete this cleaner?')">Delete</a>
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