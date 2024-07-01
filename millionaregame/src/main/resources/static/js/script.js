// Clear the sessionStorage at the beginning of your script
sessionStorage.clear();

// Initialize lifeline counts
var lifelineUsed = false;
var fiftyFiftyUsed = false;

function checkAnswer(clickedOption, correctAnswer) {
    console.log('checkAnswer called with clickedOption:', clickedOption, 'and correctAnswer:', correctAnswer);

    var buttons = document.getElementsByClassName('option');
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
        if (i === correctAnswer) {
            buttons[i].style.backgroundColor = 'green';
        } else if (i === clickedOption) {
            buttons[i].style.backgroundColor = 'red';
        }
    }

    fetch('/answer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            chosenOptionIndex: clickedOption,
        }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        window.location.href = '/game';
    })
    .catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}

function switchToTemplate(templateId) {
    // Get all templates
    var templates = document.getElementsByClassName('template');

    // Hide all templates
    for (var i = 0; i < templates.length; i++) {
        templates[i].classList.remove('active');
    }

    // Delay showing the new template to allow the transition to be seen
    setTimeout(function() {
        // Show the selected template
        var template = document.getElementById(templateId);
        template.classList.add('active');
    }, 1000);  // Delay in milliseconds
}

$(document).ready(function() {
    $('input[type="radio"]').change(function() {
        if(this.checked) {
            $('.bubble-option').css('background-color', '#2196F3'); // reset all options
            $(this).parent('.bubble-option').css('background-color', '#00ffb3'); // color the selected option
        }
    });
});

// Lifeline button click event
// Initialize lifeline counts
var lifelineUsed = localStorage.getItem('lifelineUsed') === 'true';
var fiftyFiftyUsed = localStorage.getItem('fiftyFiftyUsed') === 'true';

// Lifeline button click event
$('#life-line').click(function() {
    if (!lifelineUsed) {
        fetch('/correctAnswer')
            .then(response => response.text())
            .then(correctAnswer => {
                // Find the label containing the correct answer
                var correctOptionLabel = $('input[type="radio"]').filter(function() {
                    return this.value === correctAnswer;
                }).parent('.bubble-option');

                // Add the 'correct' class to the correct option's label
                correctOptionLabel.addClass('correct');

                lifelineUsed = true;
                // Store lifelineUsed in the localStorage
                localStorage.setItem('lifelineUsed', 'true');
            });
    } else {
        alert('No lifelines remaining');
    }
});

// 50-50 Lifeline button click event
$('#fifty-fifty').click(function() {
    if (!fiftyFiftyUsed) {
        fetch('/twoIncorrectAnswers')
            .then(response => response.json())
            .then(incorrectAnswers => {
                incorrectAnswers.forEach(incorrectAnswer => {
                    // Find the label containing the incorrect answer
                    var incorrectOptionLabel = $('input[type="radio"]').filter(function() {
                        return this.value === incorrectAnswer;
                    }).parent('.bubble-option');

                    // Add the 'incorrect' class to the incorrect option's label
                    incorrectOptionLabel.addClass('incorrect');
                });

                fiftyFiftyUsed = true; // Set fiftyFiftyUsed to true
                // Store fiftyFiftyUsed in the localStorage
                localStorage.setItem('fiftyFiftyUsed', 'true');
            });
    } else {
        alert('No 50-50 lifelines remaining');
    }
});
$(document).ready(function() {
    setInterval(function() {
        $.get("/score", function(data) {
            $("#score").text(data);
        });
    }, 1000); // Update the score every 1 second
});


    var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    stompClient.subscribe('/topic/score', function(score) {
        // Update the score on the webpage
        document.getElementById('sco-re').textContent = "Your current score is: " + score.body;
    });
});
