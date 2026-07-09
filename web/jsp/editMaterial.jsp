<%@ page import="model.Material" %>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Material</title>
</head>
<body>

<h1>Edit Material</h1>

<%
  Material m = (Material) request.getAttribute("material");
%>

<form action="${pageContext.request.contextPath}/MaterialServlet" method="POST">
  <input type="hidden" name="action" value="update">

  <input type="hidden" name="id" value="<%= m != null ? m.getId() : "" %>">

  <label>Item Name:</label>
  <input type="text" name="name" value="<%= m != null ? m.getName() : "" %>" required><br><br>

  <label>Quantity:</label>
  <input type="number" name="quantity" value="<%= m != null ? m.getQuantity() : "" %>" required><br><br>

  <label>Reorder Level:</label>
  <input type="number" name="reorderLevel" value="<%= m != null ? m.getReorderLevel() : "" %>" required><br><br>

  <button type="submit">Update Material</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/jsp/materials.jsp">Back to Inventory</a>

</body>
</html>