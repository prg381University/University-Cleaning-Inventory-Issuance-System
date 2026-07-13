<%@ page import="java.util.List" %>
<%@ page import="model.Cleaner" %>
<!DOCTYPE html>
<html>
<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>
<body>

<div class="container">
    <h2>Cleaners Management</h2>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/CleanerServlet" method="GET" style="margin-bottom: 20px;">
        <input type="hidden" name="action" value="search">
        <input type="text" name="query" placeholder="Search cleaners..." 
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn" 
           style="background-color: #6b7280;">Clear</a>
    </form>

    <!-- Add Cleaner Link -->
    <a href="${pageContext.request.contextPath}/jsp/addCleaner.jsp">Add New Cleaner</a>
    <br><br>

    <!-- Cleaners Table -->
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Department</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>

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
            <td>
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=edit&id=<%= c.getId() %>">Edit</a> |
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=delete&id=<%= c.getId() %>"
                   onclick="return confirm('Delete this cleaner?')">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn" style="background-color: #6b7280;">Back to Dashboard</a>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>