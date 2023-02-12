var STORE_ORIGIN = window.location.origin;
var top_range = 100000.00;
var ranges = [];

function formatPage() {
    initDashboard();
    loadRanges(processRanges);
    loadAssociates(formatAssociates);
};

function initDashboard() {
    $('#dashboard').hide();
    $('#dashboard').html('');
    $('#dashboard').show();
};

function loadRanges(callback) {
    var theUrl = STORE_ORIGIN + '/range/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            var ranges = jQuery.parseJSON(response.responseText);
            callback(ranges);
        }
    });
};

function processRanges(newranges) {
    ranges = newranges;
    $.each(ranges, function (index, range) {
        addRangeToDashboard(range);
    });
};

function addRangeToDashboard(range) {
    // set max salary of the dashboard
    if(range.max > top_range){
        top_range = range.max + 5000;
    }
    var scale = 1200 / top_range;
    var x1 = range.min * scale;
    var size1 = x1;
    var x2 = range.segmentOneTop * scale;
    var size2 = x2 - x1;
    var x3 = range.mid * scale;
    var size3 = x3 - x2;
    var x4 = range.segmentTwoTop * scale;
    var size4 = x4 - x3;
    var x5 = range.max * scale;
    var size5 = x5 - x4;
    var title_grade = range.title + ' (' + range.grade + ')';
    var divContent = '<table class="table table-condensed table-hover salary" id="tbl_' + range.grade + '">';
    divContent += '<thead><tr><th style="width: ' + size1 + 'px; color: red;">' + title_grade + '</th><th style="width:' + size2 + 'px">segment 1</th><th style="width:' + size3 + 'px">&nbsp</th><th style="width:' + size4 + 'px">&nbsp</th><th style="width:' + size5 + 'px">segment 3</th><th>&nbsp</th></tr></thead>';
    divContent += '<tbody id="tbl_' + range.grade + '_body"></tbody>';

    divContent += '</table>';
    $('#dashboard').append(divContent);
};

function checkSession(callback) {
    var uuid = localStorage.getItem("uuid");
    if (uuid == null){
        $('#uuidform').show();
    } else {
        callback();
    }
};

function loadAssociates(callback) {
    var uuid = localStorage.getItem("uuid");
    if (uuid == null){
        console.log("===> UUID not initialized.");
        return;
    }
    var theUrl = STORE_ORIGIN + '/associate/' + uuid + '/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            var associates = jQuery.parseJSON(response.responseText);
            callback(associates);
        }
    });
};

function formatAssociates(associates) {
    $.each(associates, function (index, associate) {
        addAssociateToDashboard(associate);
    });
};

function addAssociateToDashboard(associate){
    var scale = 1200 / top_range;
    var hrw = associate.salary * scale;
    var selector = '#tbl_' + associate.grade;
    var range = getRange(associate.grade);

    var rowContent = '<tr>';
    rowContent += '<td>' + associate.name + '</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td>';
    rowContent += '</tr>';
    rowContent += '<tr>';

    // column 0
    var rad = '4px';
    if(associate.salary >= range.min){
        rad = '4px 0px 0px 4px'
        rowContent += '<td class="bar"><hr align="left" class="correct" width=100%" style="border-radius: ' + rad +'"></td>';
    } else {
        rowContent += '<td class="bar"><hr align="left" class="correct" width="' + getHorizontalRuleWidth(0, associate.salary, range) + 'px" style="border-radius: ' + rad +'"></td>';
    }

    // column 1
    if(associate.salary >= range.segmentOneTop){
        rowContent += '<td class="bar"><hr align="left" class="correct" width=100%" style="border-radius: 0px"></td>';
    } else if(associate.salary < range.min){
        rowContent += '<td class="bar">&nbsp</td>';
    } else {
        rad = '0px 4px 4px 0px'
        rowContent += '<td class="bar"><hr align="left" class="correct" width="' + getHorizontalRuleWidth(1, associate.salary, range) + 'px" style="border-radius: ' + rad +'"></td>';
    }

    // column 2
    if(associate.salary >= range.mid){
        rowContent += '<td class="bar"><hr align="left" class="correct" width=100%" style="border-radius: 0px"></td>';
    } else if(associate.salary < range.segmentOneTop){
        rowContent += '<td class="bar">&nbsp</td>';
    } else {
        rad = '0px 4px 4px 0px'
        rowContent += '<td class="bar"><hr align="left" class="correct" width="' + getHorizontalRuleWidth(2, associate.salary, range) + 'px" style="border-radius: ' + rad +'"></td>';
    }

    // column 3
    if(associate.salary >= range.segmentTwoTop){
        rowContent += '<td class="bar"><hr align="left" class="correct" width=100%" style="border-radius: 0px"></td>';
    } else if(associate.salary < range.mid){
        rowContent += '<td class="bar">&nbsp</td>';
    } else {
        rad = '0px 4px 4px 0px'
        rowContent += '<td class="bar"><hr align="left" class="correct" width="' + getHorizontalRuleWidth(3, associate.salary, range) + 'px" style="border-radius: ' + rad +'"></td>';
    }

    // column 4
    if(associate.salary < range.segmentTwoTop){
        rowContent += '<td class="bar">&nbsp</td>';
    } else {
        rad = '0px 4px 4px 0px'
        rowContent += '<td class="bar"><hr align="left" class="correct" width="' + getHorizontalRuleWidth(4, associate.salary, range) + 'px" style="border-radius: ' + rad +'"></td>';
    }

    // column 5
    rowContent += '<td class="bar">&nbsp</td>';

    rowContent += '</tr>';

    
    $(selector).find('tbody').append(rowContent);
}

// -------------------------------------------------------------------------------------------

function getRange(grade){
    var r = null;
    $.each(ranges, function (index, range) {
        if(range.grade == grade){
            r = range;
            return;
        }
    });
    return r;
}

function getHorizontalRuleWidth(column, salary, range){
    var scale = 1200 / top_range;
    var hrv = 0;

    switch(column){
        case 0:
            hrv = salary * scale;
            break;
        case 1:
            hrv = (salary - range.min) * scale;
            break;
        case 2:
            hrv = (salary - range.segmentOneTop) * scale;
            break;
        case 3:
            hrv = (salary - range.mid) * scale;
            break;
        case 4:
            hrv = (salary - range.segmentTwoTop) * scale;
            break;
        default:
            console.log(`Warning: Value of ${column} is not correct as COLUMN.`);
    }
    return hrv;
}

// -------------------------------------------------------------------------------------------

function startIndexPage() {
    loadRanges(formatDashboard);
};

function formatDashboard(ranges, associates) {
    $('#dashboard').hide();
    $('#dashboard').html('');
    $.each(ranges, function (index, range) {
        formatRange(range, associates);
    });
    $('#dashboard').show();
};

function formatRange(range) {
    var divContent = '<p style="color: red; font-weight: bold;">';
    divContent += range.title + ' (' + range.grade + ')';
    divContent += '</p>';
    divContent += '<table class="table table-condensed table-hover" id="tbl_' + range.grade + '">';
    divContent += '<tbody id="tbl_' + range.grade + '_body"></tbody>';
    divContent += '</table>';
    console.log(divContent);
    $('#dashboard').append(divContent);
};

function storeRanges(rs) {
    $.each(rs, function (index, r) {
        if(r.grade == 'S16'){
            S16 = r;
        }
        if(r.grade == 'S17A'){
            S17A = r;
        }
    });
    loadAssociates()
};

function getUUID() {
    var theUrl = STORE_ORIGIN + '/uuid';
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            var uuid = jQuery.parseJSON(response.responseText);
            console.log("==================> " + uuid);
            localStorage.setItem("uuid", uuid);
        }
    });
};

function displayAssociates(associates) {
    $('#sa_list').hide();
    $('#seniorsa_list').hide();
    $('#sa_list').html('<p style="color: red; font-weight: bold;">Solution Architects (grade S16)</p>');
    $('#seniorsa_list').html('<p style="color: red; font-weight: bold;">Senior Solution Architects (grade S17A)</p>');
    $.each(associates, function (index, associate) {
        addSAToDiv(associate);
    });
    $('#sa_list').show(500);
    $('#seniorsa_list').show(500);
};

function addSAToDiv(associate) {
    var fondo_scala = 60000;
    var range = 129450 - fondo_scala;

    var g = {};
    if(associate.grade == 'S16'){
        g = S16;
    }
    if(associate.grade == 'S17A'){
        g = S17A;
    }

    var bar_value = ((associate.salary - fondo_scala) / range) * 100;
    
    var rowContent = '<div>' + associate.name + '</div>';
    rowContent += '<div class="progress" style="width: 900px; height: 15px;">';
    rowContent += '<div class="progress-bar progress-bar-striped ' + calculateBarType(associate, g) + ' progress-bar-animated" role="progressbar" aria-label="Animated striped example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: ' + bar_value + '%"></div>';
    rowContent += '</div><br>';

    if(associate.grade == 'S16'){
        $('#sa_list').append(rowContent);
    }
    if(associate.grade == 'S17A'){
        $('#seniorsa_list').append(rowContent);
    }
};

function calculateBarType(associate, grade) {
    var midpoint = (grade.max + grade.min) / 2;
    var segment = 1;
    
    if(associate.salary > midpoint){
        return 'bg-success';
    }

    if(associate.salary > grade.segmentOneTop){
        segment = 2;
    }
    if(associate.salary > grade.segmentTwoTop){
        segment = 3;
    }

    if(associate.segment > segment){
        return 'bg-warning';
    }

    if(associate.segment < segment){
        return 'bg-success';
    }

    if(segment == 1){
        if((associate.salary - grade.min) < 2000){
            return 'bg-info';
        } else {
            return 'bg-success';
        }
    }

    if(segment == 2){
        if((associate.salary - grade.segmentOneTop) < 2000){
            return 'bg-info';
        } else {
            return 'bg-success';
        }
    }

    return '';

};