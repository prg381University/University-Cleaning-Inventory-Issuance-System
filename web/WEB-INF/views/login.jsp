

<%@ include file="includes/Header.jsp" %>

<div class="container">
    <h1>Login</h1>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="POST">
        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn">Login</button>
        <a href="${pageContext.request.contextPath}/register" class="btn">Go to Register</a>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>