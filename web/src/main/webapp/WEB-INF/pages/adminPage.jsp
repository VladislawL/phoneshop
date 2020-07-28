<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tags:master pageTitle="Orders">
    <h3>Orders</h3>
    <table class="table">
        <thead class="thead-light">
            <tr>
                <th>Order number</th>
                <th>Customer</th>
                <th>Address</th>
                <th>Phone number</th>
                <th>Total price</th>
                <th>Status</th>
            </tr>
        </thead>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>
                    <a href="<c:url value="/admin/orders/${order.id}"/>">
                        <c:out value="${order.id}" />
                    </a>
                </td>
                <td><c:out value="${order.firstName} ${order.lastName}" /></td>
                <td><c:out value="${order.deliveryAddress}" /></td>
                <td><c:out value="${order.contactPhoneNo}" /></td>
                <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="${currencySymbol}" /></td>
                <td><c:out value="${order.status}" /></td>
            </tr>
        </c:forEach>
    </table>
</tags:master>