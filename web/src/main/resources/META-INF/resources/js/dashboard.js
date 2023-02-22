var STORE_ORIGIN = window.location.origin;

function subscribe(cf, callback) {
    var theUrl = STORE_ORIGIN + '/subscribe/' + cf;
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

function subscribeReactive(cf, callback) {
    var theUrl = STORE_ORIGIN + '/request/' + cf;
    $.ajax({
        url: theUrl,
        type: 'POST',
        dataType: 'json',
        complete: function(response, status, xhr){
            var cf = response.responseText;
            callback(cf);
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

function showReactiveSubscribeResult(cf) {
    localStorage.setItem("codiceFiscale", cf);
    var content = 'Richiesta correttamente inoltrata';
    $("#save_ok").html(content);
    $("#save_ok").fadeIn();
    window.setTimeout(function () { 
        $("#save_ok").fadeOut();
        $("#button_reactive_save_cf").prop('disabled', false);
    }, 4000);
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
            displaySubscriptions(list);
        }
    });
    setTimeout(checkActiveRequests, 5000);
}

function displaySubscriptions(list) {
    $.each(list, function (index, subscription) {
        displaySubscription(subscription);
    });
};

function displaySubscription(iscrizione) {
    var current;
    if(iscrizione.tipo == 'imperative'){
        current = $('#codiceProtocolloClassic').text();
        if(iscrizione.codiceProtocollo != current){
            $('#codiceProtocolloClassic').text(iscrizione.codiceProtocollo);
        }
    }
    if(iscrizione.tipo == 'reactive'){
        current = $('#codiceProtocolloReactive').text();
        if(iscrizione.codiceProtocollo != current){
            $('#codiceProtocolloReactive').text(iscrizione.codiceProtocollo);
        }
    }
};