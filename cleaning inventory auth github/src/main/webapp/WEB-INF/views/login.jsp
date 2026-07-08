<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="partials/common.jspf" %>
<% String pageTitle = "Login"; %>
<%@ include file="partials/layout_top.jspf" %>
<div class="auth-container">
    <section class="card">
        <h1>Staff Login</h1>
        <p>Sign in to manage cleaning inventory, stock issuances and reports.</p>
        <form method="post" action="<%= context %>/login">
            <div class="form-field">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="<%= e(request.getAttribute("username")) %>" required>
            </div>
            <div class="form-field">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="actions">
                <button type="submit">Login</button>
                <a class="btn btn-secondary" href="<%= context %>/register">Register staff member</a>
            </div>
        </form>
        <hr>
        <p><strong>Demo users:</strong></p>
        <p>Supervisor: <code>supervisor</code> / <code>Supervisor@123</code></p>
        <p>Storekeeper: <code>storekeeper</code> / <code>Store@123</code></p>
    </section>
</div>
<%@ include file="partials/layout_bottom.jspf" %>
