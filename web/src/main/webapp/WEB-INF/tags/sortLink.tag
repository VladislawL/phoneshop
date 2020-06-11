<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="
    <c:url value="/productList">
        <c:param name="query" value="${param.query}" />
        <c:param name="sortField" value="${field}" />
        <c:param name="sortOrder" value="${order}" />
    </c:url>">
    ${order eq "DESC" ? "<i class='fas fa-arrow-down'></i>" : "<i class='fas fa-arrow-up'></i>"}
</a>