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
            $("#items-number").text(msg.itemsNumber + " items");
            $("#subtotal-price").text("$" + msg.subTotalPrice);
            $quantityField.siblings(".error").text("");
        },
        error: function (request, status, error) {
            var errorMessage = JSON.parse(request.responseText);
            $quantityField.siblings(".error").text(errorMessage.message);
        }
    });
});