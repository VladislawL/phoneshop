$('input[name="add-to-cart"]').on("click", function() {
    var $this = $(this);
    var phoneId = $this.data("phoneId");
    var $quantityField = $("#" + phoneId);

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

$('input[name="order"]').on("click", function () {
    var $form = $('form[name="cartForm"]');
    $form.attr("action", contextPath + "/order");
    $form.attr("method", "GET");
});

$('input[name="update"]').on("click", function () {
    var $form = $('form[name="cartForm"]');
    $form.attr("action", contextPath + "/cart");
    $form.attr("method", "POST");
});