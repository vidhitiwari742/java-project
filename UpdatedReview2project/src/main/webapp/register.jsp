<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Movie Matrix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <a class="brand" href="${pageContext.request.contextPath}/login">Movie Matrix</a>
</header>
<div class="card">
    <h2>Create Account</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <input type="text" name="name" placeholder="Full name" required />
        <input type="email" name="email" placeholder="Email" required />
        <input type="password" name="password" placeholder="Password (min 6 chars)" required />
        <button type="submit">Register</button>
    </form>
    <p style="font-size:13px;">
        Already have an account? <a href="${pageContext.request.contextPath}/login" style="color:#e50914;">Log in</a>
    </p>
</div>
</body>
</html>
