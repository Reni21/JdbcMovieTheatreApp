function removeMovieSessionHandler(movieSessionId) {
    var sessionsIds = [movieSessionId];
    var path = $('#context').val();
    console.log(JSON.stringify(sessionsIds));
    $.ajax({
        type: 'post',
        url: 'movie-session',
        data: {sessionsIds: JSON.stringify(sessionsIds)}
    }).done(function (resp) {
        window.location.replace(path + '/schedule');
    }).fail(function (jqXHR) {
        var msg = jqXHR.responseText;
        alert(msg);
        console.log(jqXHR.status + ' ' + jqXHR.responseText);
    });
}