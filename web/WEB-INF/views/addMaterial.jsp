<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Add Material</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
  <h2 class="page-title">Add New Material</h2>

  <form action="${pageContext.request.contextPath}/MaterialServlet" method="POST">
    <input type="hidden" name="action" value="add">

    <label>Item Name:</label>
    <input type="text" name="name" required>

    <label>Quantity:</label>
    <input type="number" name="quantity" required>

    <label>Reorder Level:</label>
    <input type="number" name="reorderLevel" required>

    <button type="submit" class="btn-success">Save Material</button>
    <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn" style="background-color: #6b7280;">Cancel</a>
  </form>
</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>