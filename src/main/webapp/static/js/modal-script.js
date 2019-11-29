// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementsByClassName("myBtn")[0];

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}


// Send new session data
$(function() {
  $('form').submit(function(e) {
    var $form = $(this);
    var hours = $form.find('input[name="hours"]').val();
    var minutes = $form.find('input[name="minutes"]').val();
    var movieId = $form.find('input[name="movieId"]').val();

    $.ajax({
      type: $form.attr('method'),
      url: $form.attr('action'),
      data: $form.serialize()
    }).done(function(data) {
      var sessionId = data;
      console.log('success');
      $form.find('input[name="hours"]').val('');
      $form.find('input[name="price"]').val('');
      $form.find('input[name="minutes"]').val('');
      $('#movie_' + movieId).append($('<a class="tag" href="movie-session/' + sessionId + '">'+ hours + ':' + minutes + '</a>'));
    }).fail(function() {
      console.log('fail');
    });
    //отмена действия по умолчанию для кнопки submit
    e.preventDefault();
  });
});
