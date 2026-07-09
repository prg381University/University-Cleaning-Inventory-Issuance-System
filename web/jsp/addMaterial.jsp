<!DOCTYPE html>
<html>
<head>
  <title>Add Material</title>
</head>
<body>

<h1>Add New Material</h1>

<form action="${pageContext.request.contextPath}/MaterialServlet" method="POST">
  <input type="hidden" name="action" value="add">

  <label>Item Name:</label>
  <input type="text" name="name" required><br><br>

  <label>Quantity:</label>
  <input type="number" name="quantity" required><br><br>

  <label>Reorder Level:</label>
  <input type="number" name="reorderLevel" required><br><br>

  <button type="submit">Save Material</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/jsp/materials.jsp">Back to Inventory</a>

</body>
</html>