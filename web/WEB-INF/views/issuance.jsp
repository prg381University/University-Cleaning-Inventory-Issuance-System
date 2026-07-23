<%@ page import="java.util.List" %>
<%@ page import="model.Issuance" %>
<%@ page import="model.Material" %>
<%@ page import="model.Cleaner" %>
<!DOCTYPE html>
<html>
<%@ include file="includes/Header.jsp" %>
<%@ include file="includes/navbar.jsp" %>
<body>

<div class="container">
    <h2>Stock Issuance</h2>

    <!-- Success/Error Messages -->
    <%
        String success = (String) session.getAttribute("successMessage");
        String error = (String) session.getAttribute("errorMessage");
        if (success != null) {
    %>
        <div style="color: green; background: #d4edda; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
            <%= success %>
        </div>
    <%
        session.removeAttribute("successMessage");
        }
        if (error != null) {
    %>
        <div style="color: red; background: #f8d7da; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
            <%= error %>
        </div>
    <%
        session.removeAttribute("errorMessage");
        }
    %>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/IssuanceServlet" method="GET" style="margin-bottom: 20px;">
        <input type="hidden" name="action" value="search">
        <input type="text" name="query" placeholder="Search issuances..." 
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <button type="submit" class="btn">Search</button>
        <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list" class="btn" 
           style="background-color: #6b7280;">Clear</a>
    </form>

    <!-- Add Issuance Form -->
    <div style="border: 1px solid #ddd; padding: 15px; margin-bottom: 20px; border-radius: 5px;">
        <h3>Issue Stock to Cleaner</h3>
        <form action="${pageContext.request.contextPath}/IssuanceServlet" method="POST">
            <input type="hidden" name="action" value="add">

            <label>Material:</label>
            <select name="materialId" required>
                <option value="">Select Material</option>
                <%
                    List<Material> materials = (List<Material>) request.getAttribute("materialsList");
                    if (materials != null) {
                        for (Material m : materials) {
                %>
                    <option value="<%= m.getId() %>"><%= m.getName() %> (Stock: <%= m.getQuantity() %>)</option>
                <%
                        }
                    }
                %>
            </select>
            <br><br>

            <label>Cleaner:</label>
            <select name="cleanerId" required>
                <option value="">Select Cleaner</option>
                <%
                    List<Cleaner> cleaners = (List<Cleaner>) request.getAttribute("cleanersList");
                    if (cleaners != null) {
                        for (Cleaner c : cleaners) {
                %>
                    <option value="<%= c.getId() %>"><%= c.getFullName() %></option>
                <%
                        }
                    }
                %>
            </select>
            <br><br>

            <label>Quantity:</label>
            <input type="number" name="quantity" min="1" required>
            <br><br>

            <label>Notes:</label>
            <input type="text" name="notes" placeholder="Optional notes">
            <br><br>

            <button type="submit" class="btn" style="background-color: #28a745; color: white;">Issue Stock</button>
        </form>
    </div>

    <!-- Issuances Table -->
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Material</th>
            <th>Cleaner</th>
            <th>Quantity</th>
            <th>Date Issued</th>
            <th>Notes</th>
            <th>Actions</th>
        </tr>

        <%
            List<Issuance> list = (List<Issuance>) request.getAttribute("issuancesList");
            if (list == null || list.isEmpty()) {
        %>
        <tr>
            <td colspan="7">No issuances found.</td>
        </tr>
        <% } else {
            for (Issuance i : list) {
        %>
        <tr>
            <td><%= i.getId() %></td>
            <td><%= i.getMaterialName() %></td>
            <td><%= i.getCleanerName() %></td>
            <td><%= i.getQuantity() %></td>
            <td><%= i.getIssuedDate() %></td>
            <td><%= i.getNotes() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/IssuanceServlet?action=delete&id=<%= i.getId() %>"
                   onclick="return confirm('Delete this issuance? Stock will be returned.')">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="${pageContext.request.contextPath}/WEB-INF/views/dashboard.jsp" class="btn" style="background-color: #6b7280;">Back to Dashboard</a>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>