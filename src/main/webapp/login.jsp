<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Movie Matrix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <a class="brand" href="${pageContext.request.contextPath}/login">Movie Matrix</a>
</header>
<div class="card">
    <h2>Login</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="email" name="email" placeholder="Email" value="demo@moviematrix.com" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Log In</button>
    </form>
    <p style="margin-top:14px;font-size:13px;color:#999;">
        Demo account: demo@moviematrix.com / Password123
    </p>
    <p style="font-size:13px;">
        No account? <a href="${pageContext.request.contextPath}/register" style="color:#e50914;">Register here</a>
    </p>
</div>
</body>
</html>
