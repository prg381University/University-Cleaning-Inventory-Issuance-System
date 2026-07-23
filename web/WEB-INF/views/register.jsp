<%@ include file="includes/Header.jsp" %>

<div class="container">
    <h1>Register New User</h1>

    <% if (request.getAttribute("error") != null) { %>
    <div style="color: red; margin-bottom: 10px;">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/register" method="POST">
        <label>Full Name:</label>
        <input type="text" name="fullName" value="${fullName != null ? fullName : ''}" required>

        <label>Username:</label>
        <input type="text" name="username" value="${username != null ? username : ''}" required>

        <label>Email:</label>
        <input type="email" name="email" value="${email != null ? email : ''}" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <label>Confirm Password:</label>
        <input type="password" name="confirmPassword" required>

        <label>Role:</label>
        <select name="role" required>
            <option value="STOREKEEPER" ${role == 'STOREKEEPER' ? 'selected' : ''}>Storekeeper</option>
            <option value="SUPERVISOR" ${role == 'SUPERVISOR' ? 'selected' : ''}>Supervisor</option>
        </select>

        <button type="submit" class="btn">Register</button>
        <a href="${pageContext.request.contextPath}/login" class="btn">Back to Login</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>