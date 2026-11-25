<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Settings — Movie Matrix</title>
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
      <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
      <a href="${pageContext.request.contextPath}/movies">Catalog</a>
      <a href="${pageContext.request.contextPath}/watched">Watched</a>
      <a href="${pageContext.request.contextPath}/my-reviews">Reviews</a>
      <a class="active" href="${pageContext.request.contextPath}/settings">Settings</a>
      <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </nav>
  </header>

  <div class="container">
    <h2>Settings</h2>
    <div class="card" style="margin: 20px 0; max-width: 520px;">
      <h3 style="margin-top: 0;">Account Details</h3>
      <p><strong>Name:</strong> ${sessionScope.user.name}</p>
      <p><strong>Email:</strong> ${sessionScope.user.email}</p>

      <h3 style="margin-top: 20px;">Accent Theme</h3>
      <label for="themeSelect">Choose dark accent palette:</label>
      <select id="themeSelect">
        <option value="lavender">Dark + Light Lavender-Magenta (Default)</option>
        <option value="orchid">Dark + Soft Orchid-Rose</option>
      </select>
      <button type="button" onclick="saveTheme()">Save Theme</button>
      <p id="themeMsg" style="font-size: 13px; color: var(--accent-soft); margin-top: 8px;"></p>
    </div>
  </div>

  <script>
    const sel = document.getElementById('themeSelect');
    if (localStorage.getItem('mmTheme') === 'orchid') {
      sel.value = 'orchid';
    }
    function saveTheme() {
      if (sel.value === 'orchid') {
        localStorage.setItem('mmTheme', 'orchid');
        document.body.classList.add('theme-orchid');
      } else {
        localStorage.setItem('mmTheme', 'lavender');
        document.body.classList.remove('theme-orchid');
      }
      document.getElementById('themeMsg').textContent = 'Theme saved for this browser!';
    }
  </script>
</body>
</html>
