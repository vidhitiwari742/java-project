<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Movie Matrix — Discover Your Next Watch</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <script>
    if (localStorage.getItem('mmTheme') === 'orchid') {
      document.body.classList.add('theme-orchid');
    }
  </script>

  <header>
    <a class="brand" href="${pageContext.request.contextPath}/">
      <!-- Inline SVG Logo -->
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none" aria-hidden="true">
        <rect x="2" y="2" width="24" height="24" rx="7" fill="rgba(232,121,249,0.16)" stroke="#e879f9" stroke-width="2"/>
        <polygon points="11,8.5 20,14 11,19.5" fill="#d8b4fe"/>
      </svg>
      <span class="brand-text">Movie Matrix</span>
    </a>
    <nav>
      <% if (session.getAttribute("user") != null) { %>
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/movies">Catalog</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
      <% } else { %>
        <a href="${pageContext.request.contextPath}/login">Sign In</a>
        <a href="${pageContext.request.contextPath}/register">Create Account</a>
      <% } %>
    </nav>
  </header>

  <section class="hero">
    <div>
      <span class="hero-tag">LAVENDER-MAGENTA EDITION</span>
      <h1>Curated cinema picks, <span>tailored to your taste</span>.</h1>
      <p>
        Track every film you watch, write reviews, and get smart recommendations
        built from your viewing history and ratings.
      </p>
      <div class="hero-actions">
        <% if (session.getAttribute("user") != null) { %>
          <a class="btn" href="${pageContext.request.contextPath}/dashboard">Open Dashboard</a>
          <a class="btn btn-secondary" href="${pageContext.request.contextPath}/movies">Browse Movies</a>
        <% } else { %>
          <a class="btn" href="${pageContext.request.contextPath}/login">Sign In to Continue</a>
          <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register">Sign Up</a>
        <% } %>
      </div>
    </div>

    <div class="card" style="margin: 0; max-width: 100%;">
      <h2 style="margin-top: 0;">Quick Sign In</h2>
      <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="email" name="email" placeholder="Email" value="demo@moviematrix.com" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Sign In</button>
      </form>
      <p style="margin: 10px 0 0; font-size: 12px; color: var(--text-muted);">
        Demo credentials: <code>demo@moviematrix.com</code> / <code>Password123</code>
      </p>
    </div>
  </section>
</body>
</html>
