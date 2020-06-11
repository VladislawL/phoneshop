<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tags:master pageTitle="Product List">

  <form method="GET" class="search-form">
    <a href="#" class="search-icon">
      <i class="fas fa-search"></i>
    </a>
    <input id="searchInputField" name="query" type="text" placeholder="Search..." class="search-box" value="${param.query}">
  </form>

  <table class="table">
    <thead class="thead-light">
    <tr>
      <th>Image</th>
      <th>
        Brand
        <c:if test="${sortedFields.contains('brand')}" >
          <tags:sortLink field="brand" order="DESC" />
          <tags:sortLink field="brand" order="ASC" />
        </c:if>
      </th>
      <th>
        Model
        <c:if test="${sortedFields.contains('model')}" >
          <tags:sortLink field="model" order="DESC" />
          <tags:sortLink field="model" order="ASC" />
        </c:if>
      </th>
      <th>
        Colors
        <c:if test="${sortedFields.contains('colors')}" >
          <tags:sortLink field="colors" order="DESC" />
          <tags:sortLink field="colors" order="ASC" />
        </c:if>
      </th>
      <th>
        Display size
        <c:if test="${sortedFields.contains('displaySizeInches')}" >
          <tags:sortLink field="displaySizeInches" order="DESC" />
          <tags:sortLink field="displaySizeInches" order="ASC" />
        </c:if>
      </th>
      <th>
        Price
        <c:if test="${sortedFields.contains('price')}" >
          <tags:sortLink field="price" order="DESC" />
          <tags:sortLink field="price" order="ASC" />
        </c:if>
      </th>
      <th>Quantity</th>
      <th>Action</th>
    </tr>
    </thead>
    <c:forEach var="phone" items="${paginationData.phones}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
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
          <input id="<c:out value="${phone.id}" />" type="text" />
          <div class="error"></div>
        </td>
        <td>
          <input name="add-to-cart" type="submit" data-phone-id="<c:out value="${phone.id}" />" value="Add to Cart" />
          <input type="hidden" value="<c:out value="${phone.id}" />" />
        </td>
      </tr>
    </c:forEach>
  </table>
  <nav aria-label="Page navigation example">
    <ul class="pagination">

      <tags:pagination currentPage="${paginationData.currentPage}"
                       pagesCount="${paginationData.pagesCount}"
                       numberOfPaginatingPages="${paginationData.numberOfPaginatingPages}" />

    </ul>
  </nav>
</tags:master>