<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Supplier</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Add New Supplier</h2>

    <form action="${pageContext.request.contextPath}/SupplierServlet" method="POST">
        <input type="hidden" name="action" value="add">

        <label>Supplier Name:</label>
        <input type="text" name="supplierName" required>

        <label>Contact Person:</label>
        <input type="text" name="contactPerson">

        <label>Phone:</label>
        <input type="text" name="phone">

        <label>Email:</label>
        <input type="email" name="email">

        <label>Address:</label>
        <textarea name="address" rows="3"></textarea>

        <button type="submit" class="btn-success">Save Supplier</button>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>