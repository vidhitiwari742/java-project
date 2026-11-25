<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Dashboard — Movie Matrix</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <script>if (localStorage.getItem('mmTheme') === 'orchid') document.body.classList.add('theme-orchid');</script>
  <header>
    <a class="brand" href="${pageContext.request.contextPath}/dashboard">
      <svg width="26" height="26" viewBox="0 0 28 28" fill="none"><rect x="2" y="2" width="24" height="24" rx="7" fill="rgba(232,121,249,0.16)" stroke="#e879f9" stroke-width="2"/><polygon points="11,8.5 20,14 11,19.5" fill="#d8b4fe"/></svg>
      <span class="brand-text">Movie Matrix</span>
    </a>
    <nav>
      <a class="active" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
      <a href="${pageContext.request.contextPath}/movies">Catalog</a>
      <a href="${pageContext.request.contextPath}/recommendations">For You</a>
      <a href="${pageContext.request.contextPath}/watched">Watched</a>
      <a href="${pageContext.request.contextPath}/my-reviews">Reviews</a>
      <a href="${pageContext.request.contextPath}/settings">Settings</a>
      <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.name})</a>
    </nav>
  </header>

  <div class="container">
    <h2>Welcome back, ${sessionScope.user.name}</h2>
    <p style="color: var(--text-muted);">Your personal Movie Matrix overview.</p>

    <div class="stats-grid">
      <div class="stat-box">
        <div>Movies Watched</div>
        <div class="stat-value">${watchedCount}</div>
      </div>
      <div class="stat-box">
        <div>Reviews Written</div>
        <div class="stat-value">${reviewCount}</div>
      </div>
    </div>

    <div class="hero-actions">
      <a class="btn" href="${pageContext.request.contextPath}/movies">Browse Catalog</a>
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/watched">View Watched Movies</a>
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/my-reviews">My Reviews</a>
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/settings">Theme &amp; Settings</a>
    </div>
  </div>
</body>
</html>