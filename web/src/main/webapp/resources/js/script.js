$('input[name="add-to-cart"]').on("click", function() {
    var $this = $(this);
    var phoneId = $this.data("phoneId");
    var $quantityField = $("#" + phoneId);

    $.ajax({
        type: "POST",
        url: "ajaxCart",
        contentType: "application/json",
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $quantityField.val()
        }),
        success: function (msg) {
            var formatter = new Intl.NumberFormat(navigator.language, {
                style: 'currency',
                currency: msg.currency,
            });
            $("#items-number").text(msg.miniCart.itemsNumber + " items");
            $("#subtotal-price").text(formatter.format(msg.miniCart.subTotalPrice));
            $quantityField.siblings(".error").text("");
        },
        error: function (request, status, error) {
            var errorMessage = JSON.parse(request.responseText);
            $quantityField.siblings(".error").text(errorMessage.message);
        }
    });
});