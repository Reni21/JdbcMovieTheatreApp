
// When the user clicks the button, open the modal
$('.openModalBtn').click(function (){
    // Assign handlers immediately after making the request,
    // and remember the jqxhr object for this request
    $.get("movies")
        .done(function (resp) {
            var movies = JSON.parse(resp);
            $.each(movies, function (index, el) {
                // index is your 0-based array index
                // el is your value
                var link = '<label class="container">' + el.title +
                    '<input class="remember" type="checkbox" name="movie_ids[]" value="' + el.movieId + '" id="checkbox_1"/>' +
                    '<span class="checkmark"></span></label>';
                $('#selectedMovies').append($(link));
            });
        })
        .fail(function (jqXHR) {
            alert("error");
        });

    // modal.style.display = "block";
    $('#myModal').css('display', 'block');
});
// When the user clicks close button, close the modal
$('.close').click(function (){
    $('#myModal').css('display', 'none');
    $( "#selectedMovies" ).empty();
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function () {
        var errorsDivs = $('div.errors');
        if (errorsDivs.text().length !== 0) {
            errorsDivs.html('');
        }
};

// Send new session data
$(function () {
    $('form').submit(function (e) {
        var $form = $(this);
        if($form.getAttribute('id') === 'selectedMovies'){
            //reject default handler for submit button
            e.preventDefault();
            alert("It is movie form");
        } else {
            var movieId = $form.find('input[name="movieId"]').val();

            $($form).validate({ // initialize the plugin
                rules: {
                    hours: {
                        required: true,
                        range: [9, 22],
                        digits: true
                    },
                    minutes: {
                        required: true,
                        range: [0, 59],
                        digits: true
                    },
                    price: {
                        required: true,
                        number: true,
                        min: 0
                    }
                },
                messages: {
                    hours: {
                        required: "| " + errorsDictionary.get('hoursRequired'),
                        range: jQuery.validator.format("| " + errorsDictionary.get('hoursRange')),
                        digits: "| " + errorsDictionary.get('hoursDigits'),
                        maxlength: jQuery.validator.format("| " + errorsDictionary.get('hoursMaxlength'))
                    },
                    minutes: {
                        required: "| " + errorsDictionary.get('minutesRequired'),
                        range: jQuery.validator.format("| " + errorsDictionary.get('minutesRange')),
                        digits: "| " + errorsDictionary.get('minutesDigits'),
                        maxlength: jQuery.validator.format("| " + errorsDictionary.get('minutesMaxlength'))
                    },
                    price: {
                        required: "| " + errorsDictionary.get('priceRequired'),
                        number: "| " + errorsDictionary.get('priceNumber'),
                        min: "| " + errorsDictionary.get('priceMin')
                    }
                },
                errorLabelContainer: '#errors_' + movieId
            });

            if (!$form.valid()) {
                return false;
            }

        }
        createAndDisplayNewMovieSession($form, e);
    });
});

function createAndDisplayNewMovieSession(form, e) {
    var movieId = form.find('input[name="movieId"]').val();

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize()
    }).done(function (resp) {
        var movieSessionTimeDto = JSON.parse(resp);
        console.log('success');
        form.find('input[name="hours"]').val('');
        form.find('input[name="price"]').val('');
        form.find('input[name="minutes"]').val('');
        var link = '<a class="tag" href="movie-session/' + movieSessionTimeDto.movieSessionId + '">'
            + movieSessionTimeDto.startAt.hour + ':' + movieSessionTimeDto.startAt.minute + '</a>';
        $('#movie_' + movieSessionTimeDto.movieSessionId).append($(link));
    }).fail(function (jqXHR) {
        var msg = jqXHR.responseText;
        var er = '<span class="error">' + '| ' + msg + '</span>';
        $('#errors_' + movieId).html(er);
        $('.errors').css('display', 'block');
        console.log(jqXHR.status + ' ' + jqXHR.responseText);
    });
    //reject default handler for submit button
    e.preventDefault();
}