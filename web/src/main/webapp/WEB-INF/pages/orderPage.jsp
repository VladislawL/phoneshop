<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order">
    <a href="<c:url value="/cart" />">
        <input type="button" value="Back to cart">
    </a>
    <form:form method="post" modelAttribute="orderPageData">
        <form:errors path="orderItems" cssClass="error" />
        <table class="table">
            <thead class="thead-light">
            <c:forEach var="attribute" items="${attributes}">
                <th>
                    <c:out value="${attribute.caption}" />
                </th>
            </c:forEach>
            <th>Quantity</th>
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
                        <c:out value="${cartItem.value}" />
                    </td>
                </tr>
            </c:forEach>
        </table>
        <table class="table check-table">
            <tr>
                <td>
                    Subtotal price
                </td>
                <td>
                    <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="${currencySymbol}" />
                    <input type="hidden" name="subtotal" value="${cartPageData.subTotalPrice}" />
                </td>
            </tr>
            <tr>
                <td>
                    Delivery price
                </td>
                <td>
                    <fmt:formatNumber value="${deliveryPrice}" type="currency" currencySymbol="${currencySymbol}" />
                    <input type="hidden" name="deliveryPrice" value="${order.deliveryPrice}" />
                </td>
            </tr>
            <tr>
                <td>
                    Total price
                </td>
                <td>
                    <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="${currencySymbol}" />
                    <input type="hidden" name="totalPrice" value="${order.totalPrice}" />
                </td>
            </tr>
        </table>
        <div class="row">
            <div class="col-5">

                <div class="flex-row mb-1">
                    <div>
                        First name
                    </div>
                    <div>
                        <form:errors path="firstName" cssClass="error d-block" />
                        <form:input path="firstName" placeholder="First name" />
                    </div>
                </div>

                <div class="flex-row mb-1">
                    <div>
                        Last name
                    </div>
                    <div>
                        <form:errors path="lastName" cssClass="error d-block" />
                        <form:input path="lastName" placeholder="Last name" />
                    </div>
                </div>

                <div class="flex-row mb-1">
                    <div>
                        Delivery address
                    </div>
                    <div>
                        <form:errors path="deliveryAddress" cssClass="error d-block" />
                        <form:input path="deliveryAddress" placeholder="Delivery address" />
                    </div>
                </div>

                <div class="flex-row mb-1">
                    <div>
                        Phone
                    </div>
                    <div>
                        <form:errors path="contactPhoneNo" cssClass="error d-block" />
                        <form:input path="contactPhoneNo" placeholder="Phone" />
                    </div>
                </div>
                <input name="placeOrder" type="submit" value="Place Order" />
            </div>
        </div>
    </form:form>
</tags:master>
