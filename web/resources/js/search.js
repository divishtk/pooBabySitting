/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function filterFun(gender, age, location, skills) {
    $('#empListCards > div.empIndCard').hide();
    $('#empListCards > div.empIndCard').filter(function (index) {
        if (gender != "" & $(this).data('gender') == gender.toLowerCase()) {
            return true;
        }
        if (age != "" && $(this).data('age') == age) {
            return true;
        }
        if (location != "" && $(this).data('address').toLowerCase() == location.toLowerCase()) {
            return true;
        }
        if (skills != "" && $(this).data('skills').toLowerCase() == skills.toLowerCase()) {
            return true;
        }
    }).show();
}

$(document).ready(function () {

    var empList;
    var htmlCode;
    $.ajax({
        method: "POST",
        url: "/pooBabySitting/fetchEmployee",
//        data: {name: "John", location: "Boston"}
        dataType: 'JSON'
    }).done(function (data) {
        console.log(data);
        empList = data;
        var empImage;
        $.each(empList, function (i, emp) {
            if (emp.profileImage && emp.profileImage.binaryData.length > 0) {
                empImage = 'data:image/jpg;base64,' + btoa(String.fromCharCode.apply(null, new Uint8Array(emp.profileImage.binaryData)));
            } else {
                empImage = '../resources/images/profile.svg';
            }

            htmlCode = `
            <div class="empIndCard card shadow-sm m-2" style="width: 266px;min-height: 307px;height: auto;float: left;" 
                data-userId="${emp.userId}" data-name="${emp.name}" data-address="${emp.address.split(' ')[emp.address.split(' ').length - 1]}" 
                data-email="${emp.email}" data-contact="${emp.contact}" data-pincode="${emp.pincode}"
                data-gender="${emp.gender}" data-age="${emp.age}" data-skills="${emp.skills}" data-bio="${emp.bio}">
                
                <div class="imgContainer d-flex flex-column">
                    <img src="${empImage}" style="max-width: 266px;height: 272px;" alt="Not Uploaded"/>
                </div>
                <p class="h5 text-center m-1 pooFontGrandstander">${emp.name}</p>
                <div class="d-flex flex-column bd-highlight m-2 fs-14">
                    
                    <div class="px-2 py-1 bd-highlight d-flex justify-content-between border-bottom">
                        <div class="font-weight-bold text-dark">From</div>
                        <div class="font-weight-light text-secondary">${emp.address.split(' ')[emp.address.split(' ').length - 1]}</div>
                    </div>
                    
                </div>
                <div class="d-flex flex-column bd-highlight m-2 fs-14">
                    <div class="px-2 py-1 bd-highlight d-flex justify-content-between border-bottom">
                        <div class="font-weight-bold text-dark">Rating</div>
                        <div class="font-weight-light text-warning">
                            <i class="fas fa-star text-pinky"></i>
                            <i class="fas fa-star text-pinky"></i>
                            <i class="fas fa-star text-pinky"></i>
                        </div>
                    </div>
                    
                </div>
                <div class="d-flex justify-content-center p-2">
                    <div>
                        <button class="employeeViewButton btn btn-outline-0 border-0 btn-sm mx-2 border"><strong>View Profile</strong></button>
                    </div>
                </div>
            </div>
            `;

//
//            htmlCode = `
//            <div class="card shadow-sm m-2" style="width: 180px;min-height: 350px;height: auto;float: left">
//                <img src="${empImage}" style="max-width:180px; max-height:180px" alt="Not Uploaded"/>
//                <p class="h5 text-center m-1">${emp.name}</p>
//                <div class="d-flex flex-column bd-highlight m-2 fs-14">
//                    <div class="px-2 py-1 bd-highlight d-flex justify-content-between border-bottom">
//                        <div class="font-weight-bold text-dark">Id</div>
//                        <div class="font-weight-light text-secondary">${emp.userId}</div>
//                    </div>
//                    <div class="px-2 py-1 bd-highlight d-flex justify-content-between border-bottom">
//                        <div class="font-weight-bold text-dark">Location</div>
//                        <div class="font-weight-light text-secondary">${emp.address.split(' ')[emp.address.split(' ').length - 1]}</div>
//                    </div>
//                    <div class="px-2 py-1 bd-highlight d-flex justify-content-between border-bottom">
//                        <div class="font-weight-bold text-dark">Contact</div>
//                        <div class="font-weight-light text-secondary">${emp.contact}</div>
//                    </div>
//                </div>
//                <div class="d-flex justify-content-center p-2">
//                    <div>
//                        <button class="btn btn-outline-0 border-0 btn-sm mx-2 border"><strong>View Profile</strong></button>
//                    </div>
//                </div>
//            </div>`;

            $('#pre-loader').css('display', 'none');
            $('#empListCards').append(htmlCode);
        });
    }).fail(function (jqXHR, textStatus) {
        alert("Request failed: " + textStatus.reponseText());
    });

    $(document).on('change', '#rangeFilter', function () {
        $('#pooAge').val($(this).val());
        $('#pooAge').trigger('change');
    });

    var events = [
        {'Date': new Date(2016, 6, 7), 'Title': 'Doctor appointment at 3:25pm.'},
        {'Date': new Date(2016, 6, 18), 'Title': 'New Garfield movie comes out!', 'Link': 'https://garfield.com'},
        {'Date': new Date(2016, 6, 27), 'Title': '25 year anniversary', 'Link': 'https://www.google.com.au/#q=anniversary+gifts'}
    ];

    $(document).on('click', '.employeeViewButton', function () {
        //process data
        var emp = $(this).parents(".empIndCard");
        var name = emp.data("name");
        var email = emp.data("email");
        var address = emp.data("address");
        $('.empIndName').text(name);
        $('.empIndName').data('selId', emp);

        // Animation complete.
        $(".empListBody").animate({left: '50px', opacity: 0});
        $("#empViewBody").fadeIn(2000).animate({opacity: 1});
        $('.empListBody').css('display', 'none');
        $('#empViewBody').css('display', 'block');
    });

    $(document).on('click', '.goLeftEmp', function () {
        if ($('.empIndName').data('selId').prev().length > 0) {
            var prevEmp = $('.empIndName').data('selId').prev();
            var name = prevEmp.data("name");
            var email = prevEmp.data("email");
            var address = prevEmp.data("address");
            $('.empIndImage').attr('src', prevEmp.find('img').attr('src'));
            $('.empIndName').text(name);
            $('.empIndName').data('selId', prevEmp);
        }
    });

    $(document).on('click', '.goRightEmp', function () {
        if ($('.empIndName').data('selId').next().length > 0) {
            var nextEmp = $('.empIndName').data('selId').next();
            var name = nextEmp.data("name");
            var email = nextEmp.data("email");
            var address = nextEmp.data("address");
            $('.empIndImage').attr('src', nextEmp.find('img').attr('src'));
            $('.empIndName').text(name);
            $('.empIndName').data('selId', nextEmp);
        }
    });

    $(document).on('click', '.goBackEmpList', function () {
        $("#empViewBody").fadeOut(2000).animate({opacity: 0});
        $(".empListBody").animate({left: '0px', opacity: 1});
        $('#empViewBody').css('display', 'none');
        $('.empListBody').css('display', 'block');
    });

    $('#sortByFunction').on('click', function () {
        var sortBy = $(this).val();
        var result = "<h2> Oops, Something went Wrong!</h2>";
        switch (sortBy) {
            case "byName":
                result = $('#empListCards > div.empIndCard').sort(function (a, b) {
                    var contentA = $(a).data('name');
                    var contentB = $(b).data('name');
                    return (contentA < contentB) ? -1 : (contentA > contentB) ? 1 : 0;
                });
                break;
            case "byAge":
                result = $('#empListCards > div.empIndCard').sort(function (a, b) {
                    var contentA = Integer.parseInt($(a).data('age'));
                    var contentB = Integer.parseInt($(b).data('age'));
                    return (contentA < contentB) ? -1 : (contentA > contentB) ? 1 : 0;
                });
                break;
            case "byLoc":
                result = $('#empListCards > div.empIndCard').sort(function (a, b) {
                    var contentA = $(a).data('address');
                    var contentB = $(b).data('address');
                    return (contentA < contentB) ? -1 : (contentA > contentB) ? 1 : 0;
                });
                break;
            case "byExp":
                result = $('#empListCards > div.empIndCard').sort(function (a, b) {
                    var contentA = Integer.parseInt($(a).data('age'));
                    var contentB = Integer.parseInt($(b).data('age'));
                    return (contentA < contentB) ? -1 : (contentA > contentB) ? 1 : 0;
                });
                break;
            default:
                result = "<h2> Cannot Sort by this value!</h2>";
                console.log("No value found");
        }
        $('#empListCards').html(result);
    });

    $(document).on('change', '.empViewFilter', function () {
        console.log($(this));
        var gender = "", age = "", skills = "", location = "";
        if ($(this).data('filtertype') == "gender") {
            gender = $(this).next().text().trim();
        }
        if ($(this).data('filtertype') == "age") {
            age = $(this).val();
        }
        if ($(this).data('filtertype') == "skills") {
            skills = $(this).next().text().trim();
        }
        if ($(this).data('filtertype') == "loc") {
            location = $(this).val();
        }
        filterFun(gender, age, location, skills);
    });

    $(document).on('click', '#searchBar', function () {
        $('#empListCards > div.empIndCard').hide();
        var name;
        var nameVal = $('#searchText').val();
        $('#empListCards > div.empIndCard').filter(function (index) {
            name = $(this).data('name');

            if (name.match(new RegExp(nameVal + ".*"))) {
                return true;
            }
        }).show();
    });
});