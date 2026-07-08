<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="partials/common.jspf" %>
<% String pageTitle = "Register"; %>
<%@ include file="partials/layout_top.jspf" %>
<div class="auth-container">
    <section class="card">
        <h1>Register Staff Member</h1>
        <p>Create a user account with either Storekeeper or Supervisor permissions.</p>
        <form method="post" action="<%= context %>/register">
            <div class="form-grid">
                <div class="form-field">
                    <label for="fullName">Full name</label>
                    <input type="text" id="fullName" name="fullName" value="<%= e(request.getAttribute("fullName")) %>" required>
                </div>
                <div class="form-field">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" value="<%= e(request.getAttribute("username")) %>" required>
                </div>
                <div class="form-field">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="<%= e(request.getAttribute("email")) %>" required>
                </div>
                <div class="form-field">
                    <label for="role">Role</label>
                    <select id="role" name="role" required>
                        <option value="">Select role</option>
                        <option value="STOREKEEPER" <%= selected(request.getAttribute("role"), "STOREKEEPER") %>>Storekeeper</option>
                        <option value="SUPERVISOR" <%= selected(request.getAttribute("role"), "SUPERVISOR") %>>Supervisor</option>
                    </select>
                </div>
                <div class="form-field">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-field">
                    <label for="confirmPassword">Confirm password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
            </div>
            <div class="actions">
                <button type="submit">Register</button>
                <a class="btn btn-secondary" href="<%= context %>/login">Back to login</a>
            </div>
        </form>
    </section>
</div>
<%@ include file="partials/layout_bottom.jspf" %>
