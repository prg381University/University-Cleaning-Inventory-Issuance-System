<%@ page import="model.Material" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Material</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
  <h2 class="page-title">Edit Material</h2>

  <% Material m = (Material) request.getAttribute("material"); %>

  <form action="${pageContext.request.contextPath}/MaterialServlet" method="POST">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= m != null ? m.getId() : "" %>">

    <label>Item Name:</label>
    <input type="text" name="name" value="<%= m != null ? m.getName() : "" %>" required>

    <label>Quantity:</label>
    <input type="number" name="quantity" value="<%= m != null ? m.getQuantity() : "" %>" required>

    <label>Reorder Level:</label>
    <input type="number" name="reorderLevel" value="<%= m != null ? m.getReorderLevel() : "" %>" required>

    <button type="submit" class="btn">Update Material</button>
    <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
  </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>