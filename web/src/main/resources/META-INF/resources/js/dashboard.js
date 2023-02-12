var STORE_ORIGIN = window.location.origin;

function subscribe(cf, callback) {
    var theUrl = STORE_ORIGIN + '/subscribe/' + cf;
    console.log('=====> ' + theUrl);
    $.ajax({
        url: theUrl,
        type: 'POST',
        dataType: 'json',
        complete: function(response, status, xhr){
            var iscrizione = jQuery.parseJSON(response.responseText);
            callback(iscrizione);
        }
    });
};

function showSubscribeResult(iscrizione) {
    if(iscrizione.errore == 'nessuno'){
        localStorage.setItem("codiceFiscale", iscrizione.codiceFiscale);
        var content = 'Richiesta registrata con codice di protocollo: <b>' + iscrizione.codiceProtocollo + '</b>';
        $("#save_ok").html(content);
        $("#save_ok").fadeIn();
        window.setTimeout(function () { 
            $("#save_ok").fadeOut();
            $("#button_save_cf").prop('disabled', false);
        }, 4000);
    } else {
        var content = 'Errore: ' + iscrizione.errore;
        $("#save_error").html(content);
        $("#save_error").fadeIn();
        window.setTimeout(function () { 
            $("#save_error").fadeOut();
            $("#button_save_cf").prop('disabled', false);
        }, 2000);
    }
};

function checkActiveRequests() {
    var cf = localStorage.getItem("codiceFiscale");
    var theUrl = STORE_ORIGIN + '/check/' + cf;
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            var list = jQuery.parseJSON(response.responseText);
            displayRequestTable(list);
        }
    });
    setTimeout(checkActiveRequests, 5000);
}

function displayRequestTable(list) {
    $('#requestTableContainer').hide();
    $("#reqsTableBody").empty();
    $.each(list, function (index, iscrizione) {
        addElementToTable(iscrizione);
    });
    $('#requestTableContainer').show(500);
};

function addElementToTable(iscrizione) {
    var rowContent = '<tr>';
    rowContent += '<td>' + iscrizione.codiceProtocollo + '</td>';
    rowContent += '<td>' + iscrizione.codiceFiscale + '</td>';
    rowContent += '<td>' + iscrizione.premio + '</td>';
    rowContent += '<td>' + iscrizione.tipo + '</td>';
    rowContent += '</tr>';
    $('#tbl_reqs  tbody').append(rowContent);
};
