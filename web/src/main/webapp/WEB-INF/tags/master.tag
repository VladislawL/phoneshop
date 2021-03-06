<%@ tag trimDirectiveWhitespaces="true" pageEncoding="utf-8" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/styles/style.css"/>" />
    <title>${pageTitle}</title>
</head>
<body>

    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/productList"/>">
            <i class="fas fa-phone-square-alt"></i>
            <span>Phone Shop</span>
        </a>
        <div class="flex-row">
            <security:authorize access="isAuthenticated()">
                <div class="user-box">
                    <div>
                        <security:authentication property="principal.username"/>
                    </div>
                    <form action="<c:url value="/logout"/>" method="post" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                    </form>
                    <a href="javascript:logoutFormSubmit()">Logout</a>
                </div>
            </security:authorize>
            <a href="<c:url value="/cart"/>">
                <div class="mini-cart">
                    <i class="fas fa-shopping-cart"></i>
                    <span id="items-number"><c:out value="${miniCart.itemsNumber}"/> items</span>
                    <span id="subtotal-price"><fmt:formatNumber type="currency" currencySymbol="${currencySymbol}" value="${miniCart.subTotalPrice}"/></span>
                </div>
            </a>
        </div>
    </nav>

    <main class="container-fluid">

        <jsp:doBody />

    </main>

    <script>
        var contextPath = "${pageContext.servletContext.contextPath}";
    </script>

    <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="<c:url value="/resources/js/script.js"/>"></script>
</body>
</html>