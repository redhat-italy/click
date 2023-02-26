var STORE_ORIGIN = window.location.origin;

function loadSubscriptions(callbackFunction) {
    var theUrl = STORE_ORIGIN + '/iscrizioni';
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            var subs = jQuery.parseJSON(response.responseText);
            callbackFunction(subs);
        }
    });
};

function lottery(award, amount) {
    var theUrl = STORE_ORIGIN + '/iscrizioni/estrazione/' + award + '/' + amount;
    $.ajax({
        type: "POST",
        url: theUrl,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        complete: function(response, status){
            //do nothing here
            console.log("===> lottery requested: premio = " + award + ', qta = ' + amount);
        }
    });
};

// ========================================
// callbacks
// ========================================
function displaySubs(subs) {
    $('#subsTableContainer').hide();
    $("#subsTableBody").empty();
    $.each(subs, function (index, sub) {
        addSubToTable(sub);
    });
    $('#subsTableContainer').show(500);
};

function addSubToTable(sub) {
    var rowContent = '<tr>';
    rowContent += '<td>' + sub.codiceFiscale + '</td>';
    rowContent += '<td>' + sub.codiceProtocollo + '</td>';
    rowContent += '<td>' + sub.tipo + '</td>';
    rowContent += '<td>' + sub.premio + '</td>';
    rowContent += '</tr>';
    $('#tbl_trips  tbody').append(rowContent);
};