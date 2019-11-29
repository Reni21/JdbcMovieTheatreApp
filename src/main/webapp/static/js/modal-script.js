// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementsByClassName("myBtn")[0];

// Get the <span> element that closes the modal
var closeBtn = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
};
// When the user clicks close button, close the modal
closeBtn.onclick = function() {
  modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target === modal) {
    modal.style.display = "none";
  } else{
    var errorsDivs = $('div.errors');
    if (errorsDivs.text().length !== 0 ) {
      errorsDivs.html('');
    }
  }
};


// Send new session data
$(function() {

  $('form').submit(function(e) {
    var $form = $(this);
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
        } ,
        price: {
          required: "| " + errorsDictionary.get('priceRequired'),
          number: "| " + errorsDictionary.get('priceNumber'),
          min: "| " + errorsDictionary.get('priceMin')
        }
      },
      errorLabelContainer: '#errors_' + movieId
    });

    if (!$form.valid()){
      return false;
    }

    var hours = $form.find('input[name="hours"]').val();
    var minutes = $form.find('input[name="minutes"]').val();
    var price = $form.find('input[name="price"]').val();

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
      if(minutes < 10) {
        minutes = "0" + minutes;
      }
      $('#movie_' + movieId).append($('<a class="tag" href="movie-session/' + sessionId + '">'+ hours + ':' + minutes + '</a>'));
    }).fail(function() {
      console.log('fail');
    });
    //отмена действия по умолчанию для кнопки submit
    e.preventDefault();
  });
});