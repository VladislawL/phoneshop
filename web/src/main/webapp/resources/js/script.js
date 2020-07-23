$('input[name="add-to-cart"]').on("click", function() {
    var $this = $(this);
    var phoneId = $this.data("phoneId");
    var $quantityField = $("#" + phoneId);
    var $header = $("meta[name='_csrf_header']").attr("content");
    var $token = $("meta[name='_csrf']").attr("content");

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader($header, $token);
        }
    });

    $.ajax({
        type: "POST",
        url: contextPath + "/ajaxCart",
        contentType: "application/json",
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $quantityField.val()
        }),
        success: function (msg) {
            updateMiniCart(msg);
            $quantityField.siblings(".error").text("");
        },
        error: function (request, status, error) {
            var errorMessage = JSON.parse(request.responseText);
            $quantityField.siblings(".error").text(errorMessage.message);
        }
    });
});


$('input[name="delete-cart-item"]').on("click", function() {
    var $this = $(this);
    var phoneId = $this.data("phoneId");
    var $header = $("meta[name='_csrf_header']").attr("content");
    var $token = $("meta[name='_csrf']").attr("content");

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader($header, $token);
        }
    });

    $.ajax({
        type: "DELETE",
        url: contextPath + "/cart",
        contentType: "application/json",
        data: JSON.stringify(phoneId),
        success: function (msg) {
            updateMiniCart(msg);
            $this.parents('tr').remove();
        }
    });

    return false;
});

function updateMiniCart(msg) {
    var formatter = new Intl.NumberFormat(navigator.language, {
        style: 'currency',
        currency: msg.currency
    });
    $("#items-number").text(msg.miniCart.itemsNumber + " items");
    $("#subtotal-price").text(formatter.format(msg.miniCart.subTotalPrice));
}

function logoutFormSubmit() {
    document.getElementById("logoutForm").submit();
}