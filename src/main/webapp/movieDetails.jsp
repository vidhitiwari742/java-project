<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${movie.title} - Movie Matrix</title>
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
    <h2>${movie.title} <span class="badge">${movie.genre.display()}</span></h2>
    <p>${movie.description}</p>
    <p>Director: ${movie.director} &nbsp;|&nbsp; Year: ${movie.releaseYear} &nbsp;|&nbsp;
        <span class="rating">&#9733; ${movie.rating}/10</span></p>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/movie">
        <input type="hidden" name="movieId" value="${movie.id}" />
        <input type="hidden" name="action" value="watch" />
        <button type="submit">Mark as Watched</button>
    </form>

    <h3>Leave a Review</h3>
    <form method="post" action="${pageContext.request.contextPath}/movie">
        <input type="hidden" name="movieId" value="${movie.id}" />
        <input type="number" step="0.1" min="0" max="10" name="rating" placeholder="Rating (0-10)" required />
        <textarea name="comment" placeholder="Your thoughts..." rows="3"></textarea>
        <button type="submit">Submit Review</button>
    </form>

    <h3>Reviews</h3>
    <c:forEach var="review" items="${reviews}">
        <div class="review">
            <strong class="rating">${review.rating}/10</strong> &nbsp;
            <span>${review.comment}</span>
        </div>
    </c:forEach>
    <c:if test="${empty reviews}">
        <p>No reviews yet. Be the first!</p>
    </c:if>
</div>
</body>
</html>
