<%@ page import="model.Cleaner" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Cleaner</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Edit Cleaner</h2>

    <% Cleaner c = (Cleaner) request.getAttribute("cleaner"); %>

    <form action="${pageContext.request.contextPath}/CleanerServlet" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= c != null ? c.getId() : "" %>">

        <label>Full Name:</label>
        <input type="text" name="fullName" value="<%= c != null ? c.getFullName() : "" %>" required>

        <label>Department:</label>
        <input type="text" name="department" value="<%= c != null ? c.getDepartment() : "" %>">

        <label>Phone:</label>
        <input type="text" name="phone" value="<%= c != null ? c.getPhone() : "" %>">

        <label>Email:</label>
        <input type="email" name="email" value="<%= c != null ? c.getEmail() : "" %>">

        <button type="submit" class="btn">Update Cleaner</button>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>