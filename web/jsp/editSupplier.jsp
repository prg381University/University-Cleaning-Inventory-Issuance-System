<%@ page import="model.Supplier" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Supplier</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
    <h2 class="page-title">Edit Supplier</h2>

    <%
        Supplier s = (Supplier) request.getAttribute("editSupplier");
        if (s == null) {
            response.sendRedirect(request.getContextPath() + "/SupplierServlet?action=list");
            return;
        }
    %>

    <form action="${pageContext.request.contextPath}/SupplierServlet" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="supplierId" value="<%= s.getSupplierId() %>">

        <label>Supplier Name:</label>
        <input type="text" name="supplierName" value="<%= s.getSupplierName() %>" required>

        <label>Contact Person:</label>
        <input type="text" name="contactPerson" value="<%= s.getContactPerson() != null ? s.getContactPerson() : "" %>">

        <label>Phone:</label>
        <input type="text" name="phone" value="<%= s.getPhone() != null ? s.getPhone() : "" %>">

        <label>Email:</label>
        <input type="email" name="email" value="<%= s.getEmail() != null ? s.getEmail() : "" %>">

        <label>Address:</label>
        <textarea name="address" rows="3"><%= s.getAddress() != null ? s.getAddress() : "" %></textarea>

        <button type="submit" class="btn">Update Supplier</button>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>