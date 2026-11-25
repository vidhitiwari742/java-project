<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Movies - Movie Matrix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<script>if (localStorage.getItem('mmTheme') === 'orchid') document.body.classList.add('theme-orchid');</script>
<header>
  <a class="brand" href="${pageContext.request.contextPath}/dashboard">
    <svg width="26" height="26" viewBox="0 0 28 28" fill="none" aria-hidden="true">
      <rect x="2" y="2" width="24" height="24" rx="7" fill="rgba(232,121,249,0.16)" stroke="#e879f9" stroke-width="2"/>
      <polygon points="11,8.5 20,14 11,19.5" fill="#d8b4fe"/>
    </svg>
    <span class="brand-text">Movie Matrix</span>
  </a>
  <nav>
    <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
    <a class="active" href="${pageContext.request.contextPath}/movies">Catalog</a>
    <a href="${pageContext.request.contextPath}/recommendations">For You</a>
    <a href="${pageContext.request.contextPath}/watched">Watched</a>
    <a href="${pageContext.request.contextPath}/my-reviews">Reviews</a>
    <a href="${pageContext.request.contextPath}/settings">Settings</a>
    <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.name})</a>
  </nav>
</header>
<div class="container">
    <h2>Browse Movies</h2>
    <form class="search-bar" method="get" action="${pageContext.request.contextPath}/movies">
        <input type="text" name="q" placeholder="Search by title..." value="${query}" />
        <button type="submit">Search</button>
    </form>

    <div class="movie-grid">
        <c:forEach var="movie" items="${movies}">
            <div class="movie-card">
                <a href="${pageContext.request.contextPath}/movie?id=${movie.id}">${movie.title}</a>
                <div class="badge">${movie.genre.display()} &middot; ${movie.releaseYear}</div>
                <div class="rating">&#9733; ${movie.rating}/10</div>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty movies}">
        <p>No movies found.</p>
    </c:if>
</div>
</body>
</html>
