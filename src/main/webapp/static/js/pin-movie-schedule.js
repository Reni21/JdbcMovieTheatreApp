function pinMovie(form, event, moviesToPin, submitFormHandler) {
    var movieIds = $('input:checkbox:checked').map(function () {
        return this.value;
    }).get();
    if (movieIds.size === 0) {
        return false;
    }
    var path_param = window.location.search.substring(1); //window.location.href
    var date = path_param.split('=')[1];
    $.each(movieIds, function (index, value) {
        var movie = moviesToPin.get(parseInt(value));
        var formId = '#session-form_' + value;
        var link = '<div id="' + value + '"><div class="wrapper">' +
            '<div id="errors_' + value + '" class="errors" style="font-size: 15px;color: red;margin: 0 auto;position: relative;"></div>' +
            '</div>' +

            '<div class="movie-card">' +
            '<div class="movie-card__container" style="margin-bottom: 10px;">' +
            '<div class="movie-cover">' +
            '<button class="btn delete" onclick="removePinHandler(' + value + ')">Remove movie</button>' +
            '</div>' +

            '<div id="movie_' + value + '" class="movie-description">' +
            '<div class="movie-title">' + movie.title + '</div>' +
            '<p class="movie-duration">Duration: ' + movie.duration + ' min</p>' +
            '<form action="schedule?date=' + date + '"' +
            ' class="session-form" name="session-form" id="session-form_' + value + '" method="post">' +
            '<input id="movieId_' + value + '" type="hidden" name="movieId" value="' + value + '"/>' +
            '<input id="hours_' + value + '" class="session-field" type="number" name="hours"' +
            ' placeholder="hh" min="9" max="22" style="padding: 8px 5px; margin-right: 4px;">' +
            '<input id="minutes_' + value + '" class="session-field" type="number" name="minutes"' +
            ' placeholder="mm" min="0" max="59" style="padding: 8px 5px; margin-right: 4px;">' +
            '<input class="session-field" type="text" name="price"' +
            ' placeholder="Price 0.0" style="padding: 8px 5px; margin-right: 4px;">' +
            '<input type="submit" value="Add session" class="add">' +
            '</form>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('main').append($(link));
        $(formId).submit(submitFormHandler);
    });
    //reject default handler for submit button
    event.preventDefault();
}

function removePinHandler(movieId) {
    var timesArr = $('#movie_' + movieId).find("a");
    if (timesArr.length === 0) {
        $('#' + movieId).remove();
    } else {
        var sessionsIds = timesArr.map(function () {
            return this.id;
        }).get();

        console.log(JSON.stringify(sessionsIds));
        $.ajax({
            type: 'post',
            url: 'movie-session',
            data: {sessionsIds: JSON.stringify(sessionsIds)}
        }).done(function (resp) {
            $('#' + movieId).remove();
        }).fail(function (jqXHR) {
            var msg = jqXHR.responseText;
            var er = '<span class="error">' + '| ' + msg + '</span>';
            $('#errors_' + movieId).html(er);
            $('.errors').css('display', 'block');
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        });
    }
}

