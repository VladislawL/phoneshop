<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="<c:url value="/productList?query=${param.query}&sortField=${field}&sortOrder=${order}"/>" />
    ${order eq "DESC" ? "<i class='fas fa-arrow-down'></i>" : "<i class='fas fa-arrow-up'></i>"}
</a>