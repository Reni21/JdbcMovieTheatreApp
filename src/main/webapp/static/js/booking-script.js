var buf = document.getElementById('session_price').getAttribute('value');
var price = parseFloat(buf);
// Clicking any seat
$(".seatNumber").click(
    function () {
        if (!$(this).hasClass("seatUnavailable")) {
            // If selected, unselect it
            if ($(this).hasClass("seatSelected")) {
                var thisId = $(this).attr('id');
                $(this).removeClass("seatSelected");
                $('#seatsList .' + thisId).remove();
                // Calling functions to update checkout total and seat counter.
                $("#" + "checkbox_" + thisId).prop('checked', false);
                removeFromCheckout(price);
                refreshCounter();
                setDisabledClass();
            } else {
                if ($("#btnClear").hasClass("btn-disabled")) {
                    $("#btnClear").removeClass("btn-disabled");
                    $("#btnClear strong").removeClass("btn-disabled");
                }
                if ($("#btnCheckout").hasClass("btn-disabled")) {
                    $("#btnCheckout").removeClass("btn-disabled");
                    $("#btnCheckout").addClass("primary-active");
                }
                // else, select it
                // getting values from Seat
                var thisId = $(this).attr('id');
                var id = thisId.split("_");
                var seatDetails = msgDictionary.get('row') + ": " + id[0] + " &ensp;| &ensp;" + msgDictionary.get('seat') + ": " + id[1] + " &ensp;| &ensp;" + msgDictionary.get('price') + ": " + price;

                // Adding this seat to the list
                var seatDetails = msgDictionary.get('row') + ": " + id[0] + " &ensp;| &ensp;" + msgDictionary.get('seat') + ": " + id[1] + " &ensp;| &ensp;" + msgDictionary.get('price') + ": " + price;
                $("#seatsList").append('<li value=' + $(this).attr('value') + ' class=' + thisId + '>' + seatDetails + "  " +
                    "<button id='remove:" + thisId + "' class='btn btn-default btn-sm removeSeat' value='" + $(this).attr('value') + "'><strong>X</strong></button></li>");
                $(this).addClass("seatSelected");
                $("#" + "checkbox_" + thisId).prop('checked', true);

                addToCheckout(price);
                refreshCounter();
            }
        }
    }
);
// Clicking any of the dynamically-generated X buttons on the list
$(document).on('click', ".removeSeat", function () {
        // Getting the Id of the Seat
        var id = $(this).attr('id').split(":");
        $('#seatsList .' + id[1]).remove();
        $("#" + id[1] + ".seatNumber").removeClass("seatSelected");
        $("#" + "checkbox_" + id[1]).prop('checked', false);
        removeFromCheckout(price);
        refreshCounter();
    }
);
// Show tooltip on hover.
$(".seatNumber").hover(
    function () {
        if (!$(this).hasClass("seatUnavailable")) {
            var id = $(this).attr('id');
            var id = id.split("_");
            var tooltip = msgDictionary.get('row') + ": " + id[0] + "    |    " + msgDictionary.get('seat') + ":" + id[1] + "    |    " + msgDictionary.get('price') + ": " + price;

            $(this).prop('title', tooltip);
        } else {
            $(this).prop('title', "Seat unavailable");
        }
    }
);

// Function to refresh seats counter
function refreshCounter() {
    $(".seatsAmount").text($(".seatSelected").length);
}

// Add seat to checkout
function addToCheckout(thisSeat) {
    var seatPrice = parseInt(thisSeat);
    var num = parseInt($('.txtSubTotal').text());
    num += seatPrice;
    num = num.toString();
    $('.txtSubTotal').text(num);
}

// Remove seat from checkout
function removeFromCheckout(thisSeat) {
    var seatPrice = parseInt(thisSeat);
    var num = parseInt($('.txtSubTotal').text());
    num -= seatPrice;
    num = num.toString();
    $('.txtSubTotal').text(num);

    setDisabledClass();
}

// Clear seats.
$("#btnClear").click(
    function () {
        $('.txtSubTotal').text(0);
        $(".seatsAmount").text(0);
        $('.seatSelected').removeClass('seatSelected');
        $('.booked_seats_checkbox').prop('checked', false);
        $('#seatsList li').remove();

        setDisabledClass();
    }
);

function setDisabledClass() {

    var selectedSeats = parseInt($(".seatSelected").length);
    if (selectedSeats === 0) {
        $("#btnClear").addClass("btn-disabled");
        $("#btnClear strong").addClass("btn-disabled");
        $("#btnCheckout").addClass("btn-disabled");
        $("#btnCheckout").removeClass("primary-active");
    }

}