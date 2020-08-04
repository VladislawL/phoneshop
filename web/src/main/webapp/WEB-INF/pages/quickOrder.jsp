<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order">
    <form:form name="quickOrderForm" method="post" modelAttribute="quickOrder">
        <table class="table">
            <tr>
                <td>Product code</td>
                <td>QTY</td>
            </tr>
            <c:forEach varStatus="status" var="cartItem" items="${quickOrder.cartItems}">
                <tr>
                    <td>
                        <form:input path="cartItems[${status.index}].phoneId" value="${cartItem.phoneId}" />
                        <div class="error">
                            <form:errors path="cartItems[${status.index}].phoneId" />
                        </div>
                    </td>
                    <td>
                        <c:if test="${cartItem.quantity == 0}">
                            <form:input path="cartItems[${status.index}].quantity"/>
                        </c:if>
                        <c:if test="${cartItem.quantity != 0}">
                            <form:input path="cartItems[${status.index}].quantity" value="${cartItem.quantity}"/>
                        </c:if>

                        <div class="error">
                            <form:errors path="cartItems[${status.index}].quantity" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input name="add2Cart" type="submit" value="Add 2 cart" />
    </form:form>
</tags:master>
