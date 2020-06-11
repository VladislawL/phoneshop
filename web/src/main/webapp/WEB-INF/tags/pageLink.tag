<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageText" required="true"%>
<%@ attribute name="pageNumber" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="<c:url value="/productList">
        <c:param name="query" value="${param.query}" />
        <c:param name="sortField" value="${param.sortField}" />
        <c:param name="sortOrder" value="${param.sortOrder}" />
        <c:param name="page" value="${pageNumber}" />
    </c:url>" class="page-link" >
    <c:out value="${pageText}" />
</a>