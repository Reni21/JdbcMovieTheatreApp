// When the user clicks the button, open the modal
$('.openModalBtn').click(function () {
    $('#myModal').css('display', 'block');
});
// When the user clicks close button, close the modal
$('.close').click(function () {
    $('#myModal').css('display', 'none');
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function () {
    // var errorsDivs = $('div.errors');
    // if (errorsDivs.text().length !== 0) {
    //     errorsDivs.html('');
    // }
};

function submitFormHandler(event) {
    var $form = $(this);

    // var movieId = $form.find('input[name="movieId"]').val();
    // $($form).validate({ // initialize the plugin
    //     rules: {
    //         hours: {
    //             required: true,
    //             range: [9, 22],
    //             digits: true
    //         },
    //         minutes: {
    //             required: true,
    //             range: [0, 59],
    //             digits: true
    //         },
    //         price: {
    //             required: true,
    //             number: true,
    //             min: 0
    //         }
    //     },
    //     messages: {
    //         hours: {
    //             required: "| " + errorsDictionary.get('hoursRequired'),
    //             range: jQuery.validator.format("| " + errorsDictionary.get('hoursRange')),
    //             digits: "| " + errorsDictionary.get('hoursDigits'),
    //             maxlength: jQuery.validator.format("| " + errorsDictionary.get('hoursMaxlength'))
    //         },
    //         minutes: {
    //             required: "| " + errorsDictionary.get('minutesRequired'),
    //             range: jQuery.validator.format("| " + errorsDictionary.get('minutesRange')),
    //             digits: "| " + errorsDictionary.get('minutesDigits'),
    //             maxlength: jQuery.validator.format("| " + errorsDictionary.get('minutesMaxlength'))
    //         },
    //         price: {
    //             required: "| " + errorsDictionary.get('priceRequired'),
    //             number: "| " + errorsDictionary.get('priceNumber'),
    //             min: "| " + errorsDictionary.get('priceMin')
    //         }
    //     },
    //     errorLabelContainer: '#errors_' + movieId
    // });
    //
    // if (!$form.valid()) {
    //     return false;
    // }
    // createAndDisplayNewMovie($form, event);
}

function createAndDisplayNewMovie(form, event) {
    var movieId = form.find('input[name="movieId"]').val();

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize()
    }).done(function (resp) {
        var movieSessionTimeDto = JSON.parse(resp);
        console.log(movieSessionTimeDto);
        form.find('input[name="hours"]').val('');
        form.find('input[name="price"]').val('');
        form.find('input[name="minutes"]').val('');
        var link = '<a class="tag" href="movie-session/' + movieSessionTimeDto.movieSessionId + '" id="' + movieSessionTimeDto.movieSessionId + '">' + movieSessionTimeDto.timeView + '</a>';
        $('#movie_' + movieId).append($(link));
    }).fail(function (jqXHR) {
        var msg = jqXHR.responseText;
        var er = '<span class="error">' + '| ' + msg + '</span>';
        $('#errors_' + movieId).html(er);
        $('.errors').css('display', 'block');
        console.log(jqXHR.status + ' ' + jqXHR.responseText);
    });
    //reject default handler for submit button
    event.preventDefault();
}

$(function () {
    $('form').submit(submitFormHandler);
});