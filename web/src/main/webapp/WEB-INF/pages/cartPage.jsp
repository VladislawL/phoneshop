<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tags:master pageTitle="Cart">
    <a href="<c:url value="/productList" />">
        <input type="button" value="Back to product list">
    </a>
    <c:choose>
        <c:when test="${cartPageData.cartItems.size() == 0}">
            <div>
                <spring:message code="cart.is.empty" />
            </div>
        </c:when>
        <c:otherwise>
            <form:form name="cartForm" method="post" modelAttribute="cartPageData">
                <table class="table">
                    <thead class="thead-light">
                    <c:forEach var="attribute" items="${attributes}">
                        <th>
                            <c:out value="${attribute.caption}" />
                        </th>
                    </c:forEach>
                    <th>Quantity</th>
                    <th>Action</th>
                    </thead>
                    <c:forEach var="cartItem" items="${cartPageData.cartItems}">
                        <tr>
                            <td>
                                <a href="<c:url value="/productDetails/${cartItem.key.id}"/>">
                                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.key.imageUrl}">
                                </a>
                            </td>
                            <td><c:out value="${cartItem.key.brand}" /></td>
                            <td><c:out value="${cartItem.key.model}" /></td>
                            <td>
                                <c:forEach var="color" items="${cartItem.key.colors}">
                                    <c:out value="${color.code}" />
                                </c:forEach>
                            </td>
                            <td><c:out value="${cartItem.key.displaySizeInches}" />"</td>
                            <td><fmt:formatNumber value="${cartItem.key.price}" type="currency" currencySymbol="${currencySymbol}" /></td>
                            <td>
                                <form:input path="cartItems[${cartItem.key.id}]" value="${cartPageData.cartItems.get(cartItem.key.id)}" />
                                <div class="error">
                                    <form:errors path="cartItems[${cartItem.key.id}]" cssClass="error" />
                                </div>
                            </td>
                            <td>
                                <input name="delete-cart-item" type="submit" data-phone-id="<c:out value="${cartItem.key.id}" />" value="Delete" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="flex-row">
                    <div class="subtotal-price">
                        <span>Price: <fmt:formatNumber value="${cartPageData.subTotalPrice}" type="currency" currencySymbol="${currencySymbol}" /></span>
                    </div>
                    <div class="cart-action">
                        <input name="update" type="submit" value="Update" />
                        <a href="<c:url value="/order"/>">
                            <input name ="order" type="button" value="Order" />
                        </a>
                    </div>
                </div>
            </form:form>
        </c:otherwise>
    </c:choose>
</tags:master>
