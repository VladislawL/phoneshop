<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order #${order.id}">
    <a href="<c:url value="/productList" />">
        <input type="button" value="Back to products">
    </a>
    <h3>Order number: <c:out value="${order.id}" /></h3>
    <table class="table">
        <thead class="thead-light">
        <c:forEach var="attribute" items="${attributes}">
            <th>
                <c:out value="${attribute.caption}" />
            </th>
        </c:forEach>
        <th>Quantity</th>
        </thead>
        <c:forEach var="orderItem" items="${order.orderItems}">
            <tr>
                <td>
                    <a href="<c:url value="/productDetails/${orderItem.phone.id}"/>">
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}">
                    </a>
                </td>
                <td><c:out value="${orderItem.phone.brand}" /></td>
                <td><c:out value="${orderItem.phone.model}" /></td>
                <td>
                    <c:forEach var="color" items="${orderItem.phone.colors}">
                        <c:out value="${color.code}" />
                    </c:forEach>
                </td>
                <td><c:out value="${orderItem.phone.displaySizeInches}" />"</td>
                <td><fmt:formatNumber value="${orderItem.phone.price}" type="currency" currencySymbol="${currencySymbol}" /></td>
                <td>
                    <c:out value="${orderItem.quantity}" />
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
                <input type="hidden" name="subtotal" value="${order.subtotal}" />
            </td>
        </tr>
        <tr>
            <td>
                Delivery price
            </td>
            <td>
                <fmt:formatNumber value="${order.deliveryPrice}" type="currency" currencySymbol="${currencySymbol}" />
                <input type="hidden" name="deliveryPrice" value="${order.deliveryPrice}" />
            </td>
        </tr>
        <tr>
            <td>
                Total price
            </td>
            <td>
                <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="${currencySymbol}" />
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
                    ${order.firstName}
                </div>
            </div>

            <div class="flex-row mb-1">
                <div>
                    Last name
                </div>
                <div>
                    ${order.lastName}
                </div>
            </div>

            <div class="flex-row mb-1">
                <div>
                    Delivery address
                </div>
                <div>
                    ${order.deliveryAddress}
                </div>
            </div>

            <div class="flex-row mb-1">
                <div>
                    Phone
                </div>
                <div>
                    ${order.contactPhoneNo}
                </div>
            </div>
        </div>
    </div>
</tags:master>
