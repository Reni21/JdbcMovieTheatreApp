var moviesToPin = new Map();
// When the user clicks the button, open the modal
$('.openModalBtn').click(function () {
    // Get all movies from server
    $.get("movies?simpleView=true")
        .done(function (resp) {
            var movies = JSON.parse(resp);
            var count = 0;
            $.each(movies, function (index, movie) {
                var mainHtmlId = 'movie_' + movie.movieId;
                if ($("#" + mainHtmlId).length === 0) {
                    // Get movie tha can be pined
                    var link = '<label class="container">' + movie.title +
                        '<input class="remember" type="checkbox" name="movie_ids" value="' + movie.movieId + '" id="checkbox_' + movie.movieId + '"/>' +
                        '<span class="checkmark"></span></label>';
                    $('#selectedMovies').append($(link));
                    count++;
                    moviesToPin.set(movie.movieId, movie);
                }
            });
            if (count === 0) {
                var html = '<p style="text-align: center; margin-top: 150px; color: grey; font-weight: 600">All movies are already pined</p>';
                $('#selectedMovies').append($(html));
            }
        })
        .fail(function (jqXHR) {
            alert("error");
        });
    $('#myModal').css('display', 'block');
});
// When the user clicks close button, close the modal
$('.close').click(function () {
    $('#myModal').css('display', 'none');
    $("#selectedMovies").empty();
    moviesToPin.clear();
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function () {
    var errorsDivs = $('div.errors');
    if (errorsDivs.text().length !== 0) {
        errorsDivs.html('');
    }
};

function submitFormHandler(event) {
    var $form = $(this);
    if ($form.attr('id') === 'selectedMovies') {
        pinMovie($form, event, moviesToPin, submitFormHandler);
        //reject default handler for submit button
        $('#myModal').css('display', 'none');
        $("#selectedMovies").empty();
        moviesToPin.clear();
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
        createAndDisplayNewMovieSession($form, event);
    }
}

function createAndDisplayNewMovieSession(form, event) {
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