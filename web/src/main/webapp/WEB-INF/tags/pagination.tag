<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="pagesCount" required="true" %>
<%@ attribute name="numberOfPaginatingPages" required="true" %>

<c:if test="${currentPage != 1}">
    <li class="page-item"><tags:pageLink pageText="<<" pageNumber="${currentPage - 1}"/></li>
</c:if>

<c:choose>

    <c:when test="${currentPage - numberOfPaginatingPages / 2 <= 0}">
        <c:forEach begin="1" var="pageNumber" end="${pagesCount > numberOfPaginatingPages ? 10 : pagesCount}">
            <li class="page-item <c:if test="${pageNumber == currentPage}">active</c:if>"><tags:pageLink pageNumber="${pageNumber}" pageText="${pageNumber}"/></li>
        </c:forEach>
        <li class="page-item disabled"><tags:pageLink pageText="..." pageNumber=""/></li>
        <li class="page-item"><tags:pageLink pageText="${pagesCount}" pageNumber="${pagesCount}"/></li>
    </c:when>

    <c:when test="${currentPage + numberOfPaginatingPages / 2 >= pagesCount}">
        <li class="page-item"><tags:pageLink pageText="1" pageNumber="1"/></li>
        <li class="page-item disabled"><tags:pageLink pageText="..." pageNumber=""/></li>
        <c:forEach begin="${pagesCount <= numberOfPaginatingPages ? 1 : pagesCount - numberOfPaginatingPages}" var="pageNumber" end="${pagesCount}">
            <li class="page-item <c:if test="${pageNumber == currentPage}">active</c:if>"><tags:pageLink pageText="${pageNumber}" pageNumber="${pageNumber}"/></li>
        </c:forEach>
    </c:when>

    <c:otherwise>
        <li class="page-item"><tags:pageLink pageText="1" pageNumber="1"/></li>
        <li class="page-item disabled"><tags:pageLink pageText="..." pageNumber=""/></li>
        <c:forEach begin="${currentPage - numberOfPaginatingPages / 2 + 1}" var="productListPage" end="${currentPage + numberOfPaginatingPages / 2 - 1}">
            <li class="page-item <c:if test="${productListPage == currentPage}">active</c:if>"><tags:pageLink pageText="${productListPage}" pageNumber="${productListPage}"/></li>
        </c:forEach>
        <li class="page-item disabled"><tags:pageLink pageText="..." pageNumber=""/></li>
        <li class="page-item"><tags:pageLink pageText="${pagesCount}" pageNumber="${pagesCount}"/></li>
    </c:otherwise>

</c:choose>

<c:if test="${currentPage != pagesCount}">
    <li class="page-item"><tags:pageLink pageText=">>" pageNumber="${currentPage + 1}"/></li>
</c:if>
