<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>For You - Movie Matrix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <a class="brand" href="${pageContext.request.contextPath}/movies">Movie Matrix</a>
    <nav>
        <a href="${pageContext.request.contextPath}/movies">Catalog</a>
        <a href="${pageContext.request.contextPath}/recommendations">For You</a>
        <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.name})</a>
    </nav>
</header>
<div class="container">
    <h2>Recommended For You</h2>
    <p style="color:#999;">Personalized using your watch history and review ratings.</p>

    <div class="movie-grid">
        <c:forEach var="movie" items="${recommendations}">
            <div class="movie-card">
                <a href="${pageContext.request.contextPath}/movie?id=${movie.id}">${movie.title}</a>
                <div class="badge">${movie.genre.display()} &middot; ${movie.releaseYear}</div>
                <div class="rating">&#9733; ${movie.rating}/10</div>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty recommendations}">
        <p>Watch and review a few movies to get personalized recommendations.</p>
    </c:if>
</div>
</body>
</html>
