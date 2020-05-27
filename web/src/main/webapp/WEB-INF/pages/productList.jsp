<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">

  <form id="searchForm" name="searchform" action="#" method="GET" class="search-form">
    <a href="#" class="search-icon">
      <i class="fas fa-search"></i>
    </a>
    <input id="searchInputField" name="searchInputField" type="text" placeholder="Search..." class="search-box">
  </form>

  <table class="table">
    <thead class="thead-light">
    <tr>
      <th>Image</th>
      <th>
        Brand
        <tags:sortLink field="BRAND" order="DESC" />
        <tags:sortLink field="BRAND" order="ASC" />
      </th>
      <th>
        Model
        <tags:sortLink field="MODEL" order="DESC" />
        <tags:sortLink field="MODEL" order="ASC" />
      </th>
      <th>Colors</th>
      <th>
        Display size
        <tags:sortLink field="DISPLAY_SIZE_INCHES" order="DESC" />
        <tags:sortLink field="DISPLAY_SIZE_INCHES" order="ASC" />
      </th>
      <th>
        Price
        <tags:sortLink field="PRICE" order="DESC" />
        <tags:sortLink field="PRICE" order="ASC" />
      </th>
      <th>Quantity</th>
      <th>Action</th>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        </td>
        <td>${phone.brand}</td>
        <td>${phone.model}</td>
        <td>
          <c:forEach var="color" items="${phone.colors}">
            ${color.code}
          </c:forEach>
        </td>
        <td>${phone.displaySizeInches}"</td>
        <td>$ ${phone.price}</td>
        <td><input id="${phone.id}" type="text" /></td>
        <td>
          <input type="submit" value="Add to Cart" />
          <input type="hidden" value="${phone.id}" />
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>