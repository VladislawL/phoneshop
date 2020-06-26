<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="${phone.model}">
    <a href="<c:url value="/productList" />">
        <input type="button" value="Back to product list">
    </a>
    <div class="product-details">
        <div class="row">
            <div class="col-6">
                <div class="image">
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </div>
                <div class="description">
                    <c:out value="${phone.description}" />
                </div>
                <div class="add-to-cart">
                    <div class="price">
                        <span>Price</span>
                        <span><c:out value="${phone.price}" /></span>
                    </div>
                    <div class="add-button">
                        <input id="${phone.id}" type="text" />
                        <input name="add-to-cart" type="submit" data-phone-id="${phone.id}" value="Add to cart" />
                        <div class="error"></div>
                    </div>
                </div>
            </div>
            <div class="col-6">
                Display
                <table class="table table-bordered">
                    <tr>
                        <td>Size</td>
                        <td><c:out value="${phone.displaySizeInches}"/></td>
                    </tr>
                    <tr>
                        <td>Resolution</td>
                        <td><c:out value="${phone.displayResolution}"/></td>
                    </tr>
                    <tr>
                        <td>Technology</td>
                        <td><c:out value="${phone.displayTechnology}"/></td>
                    </tr>
                    <tr>
                        <td>Pixel density</td>
                        <td><c:out value="${phone.pixelDensity}"/></td>
                    </tr>
                </table>

                Dimensions & weight
                <table class="table table-bordered">
                    <tr>
                        <td>Length</td>
                        <td><c:out value="${phone.lengthMm}"/></td>
                    </tr>
                    <tr>
                        <td>Width</td>
                        <td><c:out value="${phone.widthMm}"/></td>
                    </tr>
                    <tr>
                        <td>Weight</td>
                        <td><c:out value="${phone.weightGr}"/></td>
                    </tr>
                </table>

                Camera
                <table class="table table-bordered">
                    <tr>
                        <td>Front</td>
                        <td><c:out value="${phone.frontCameraMegapixels}"/></td>
                    </tr>
                    <tr>
                        <td>Back</td>
                        <td><c:out value="${phone.backCameraMegapixels}"/></td>
                    </tr>
                </table>

                Battery
                <table class="table table-bordered">
                    <tr>
                        <td>Talk time</td>
                        <td><c:out value="${phone.talkTimeHours}"/></td>
                    </tr>
                    <tr>
                        <td>Stand by time</td>
                        <td><c:out value="${phone.standByTimeHours}"/></td>
                    </tr>
                    <tr>
                        <td>Battery capacity</td>
                        <td><c:out value="${phone.batteryCapacityMah}"/></td>
                    </tr>
                </table>

                Other
                <table class="table table-bordered">
                    <tr>
                        <td>Colors</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}">
                                <c:out value="${color.code} "/>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Device type</td>
                        <td><c:out value="${phone.deviceType}"/></td>
                    </tr>
                    <tr>
                        <td>Bluetooth</td>
                        <td><c:out value="${phone.bluetooth}"/></td>
                    </tr>
                </table>

            </div>
        </div>
    </div>
</tags:master>
