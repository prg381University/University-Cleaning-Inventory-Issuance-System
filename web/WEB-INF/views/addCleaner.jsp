<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Cleaner</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Add New Cleaner</h2>

    <form action="${pageContext.request.contextPath}/CleanerServlet" method="POST">
        <input type="hidden" name="action" value="add">

        <label>Full Name:</label>
        <input type="text" name="fullName" required>

        <label>Department:</label>
        <input type="text" name="department">

        <label>Phone:</label>
        <input type="text" name="phone">

        <label>Email:</label>
        <input type="email" name="email">

        <button type="submit" class="btn-success">Save Cleaner</button>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>