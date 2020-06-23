<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Cart">
    <a href="<c:url value="/productList" />">
        <input type="button" value="Back to product list">
    </a>
    <form:form method="post" modelAttribute="cartPageData">
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
            <c:forEach var="phone" items="${phones}">
                <tr>
                    <td>
                        <a href="<c:url value="/productDetails/${phone.id}"/>">
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </a>
                    </td>
                    <td><c:out value="${phone.brand}" /></td>
                    <td><c:out value="${phone.model}" /></td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            <c:out value="${color.code}" />
                        </c:forEach>
                    </td>
                    <td><c:out value="${phone.displaySizeInches}" />"</td>
                    <td><fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="${currencySymbol}" /></td>
                    <td>
                        <form:input path="cartItems[${phone.id}]" value="${cartPageData.cartItems.get(phone.id)}" />
                        <div class="error">
                            <form:errors path="cartItems[${phone.id}]" cssClass="error" />
                        </div>
                    </td>
                    <td>
                        <input name="delete-cart-item" type="submit" data-phone-id="<c:out value="${phone.id}" />" value="Delete" />
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div class="flex-row">
            <div class="subtotal-price">
                <span>Price: <c:out value="${cart.subTotalPrice}" /></span>
            </div>
            <div class="cart-action">
                <input name="update" type="submit" value="Update" />
                <a href="<c:url value="/order" />">
                    <input type="button" value="order">
                </a>
            </div>
        </div>
    </form:form>
</tags:master>
