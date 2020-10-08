/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $.ajax({
        method: "GET",
        url: "/pooBabySitting/notifications?action=all",
        dataType: 'JSON'
    }).done(function (data) {
        console.log(data);
        $('.list-group-all-notify').empty();
        $('.list-group-accepted-notify').empty();
        $('.list-group-rejected-notify').empty();
        $('.list-group-pending-notify').empty();
        var html = "";
        $.each(data, function (i, v) {

            if (v.hired_date) {
                var hiredDate = moment(moment(v.hired_date, 'DD-MM-YYYY').toDate()).format("Do MMMM YYYY");
            }
            if (v.sent_date) {
                var sentDate = moment(moment(v.sent_date, 'DD-MM-YYYY').toDate()).fromNow();
            }
            if(v.status == "ACCEPTED"){
                html = `<li class="list-group-item">
                    <div class="row">
                    <div class="col">
                        <p class="h6">
                            <i class="far fa-check-circle text-success"></i>
                            Offer accepted from ${v.from_id} for $${v.og_amount1} to be hired on ${hiredDate}<br>
                            <span class="text-lead fs-8 timeSnippet">${sentDate}</span>
                        </p>
                    </div>
                    </div>
                    </li>`;
                $('.list-group-accepted-notify').append(html);
            }else if(v.status == "REJECTED"){
                html = `<li class="list-group-item">
                    <div class="row">
                    <div class="col">
                        <p class="h6">
                            <i class="far fa-times-circle text-danger"></i>
                            Rejected offer on ${hiredDate} from ${v.from_id} for $${v.og_amount1}<br>
                            <span class="text-lead fs-8 timeSnippet">${sentDate}</span>
                        </p>
                    </div>
                    </div>
                    </li>`;
                $('.list-group-rejected-notify').append(html);
            }else if(v.status == "PENDING"){
                html = `<li class="list-group-item">
                    <div class="row">
                    <div class="col">
                        <p class="h6">
                            <i class="far fa-clock text-info"></i>
                            Received an offer from ${v.from_id} for $${v.og_amount1} to be hired on ${hiredDate}<br>
                            <span class="text-lead fs-8 timeSnippet" >${sentDate}</span>
                        </p>
                    </div>
                    </div>
                    </li>`;
                $('.list-group-pending-notify').append(html);
            }
            $('.list-group-all-notify').append(html);

        })
    }).fail(function (jqXHR, textStatus) {
        alert("Request failed: " + textStatus.reponseText());
    });
});