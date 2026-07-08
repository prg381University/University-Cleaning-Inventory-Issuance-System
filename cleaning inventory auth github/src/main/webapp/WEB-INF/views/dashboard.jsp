<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="partials/common.jspf" %>
<% String pageTitle = "Dashboard"; %>
<%@ include file="partials/layout_top.jspf" %>
<section class="card">
    <h1>Authentication Successful</h1>
    <p>You are logged in to the protected dashboard page.</p>

    <% if (loggedInUser != null) { %>
    <div class="grid">
        <div class="stat-card">
            <p>Full Name</p>
            <div class="stat-number" style="font-size: 22px;"><%= e(loggedInUser.getFullName()) %></div>
        </div>
        <div class="stat-card">
            <p>Username</p>
            <div class="stat-number" style="font-size: 22px;"><%= e(loggedInUser.getUsername()) %></div>
        </div>
        <div class="stat-card">
            <p>Role</p>
            <div class="stat-number" style="font-size: 22px;"><%= e(loggedInUser.getRole()) %></div>
        </div>
    </div>
    <% } %>

    <div class="actions">
        <a class="btn" href="<%= context %>/logout">Logout</a>
    </div>
</section>

<%@ include file="partials/layout_bottom.jspf" %>
