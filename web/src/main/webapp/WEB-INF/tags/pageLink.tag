<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageText" required="true"%>
<%@ attribute name="pageNumber" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="<c:url value="/productList?query=${param.query}&sortField=${param.sortField}&sortOrder=${param.sortOrder}&page=${pageNumber}"/>" class="page-link" />
    ${pageText}
</a>