$(document).ready(function () {
    var base_color = "rgb(230,230,230)";
//    var base_color = "antiquewhite";
//    var active_color = "rgb(237, 40, 70)";
    var active_color = "#e75480";


    var child = 1;
    var length = $("section").length - 1;
    $("#prev").addClass("disabled");
    $("#submit").addClass("disabled");

    $("section").not("section:nth-of-type(1)").hide();
    $("section").not("section:nth-of-type(1)").css('transform', 'translateX(100px)');

    var svgWidth = length * 200 + 24;
    $("#svg_wrap").html(
            '<svg version="1.1" id="svg_form_time" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 ' +
            svgWidth +
            ' 24" xml:space="preserve"></svg>'
            );

    function makeSVG(tag, attrs) {
        var el = document.createElementNS("http://www.w3.org/2000/svg", tag);
        for (var k in attrs)
            el.setAttribute(k, attrs[k]);
        return el;
    }

    for (i = 0; i < length; i++) {
        var positionX = 12 + i * 200;
        var rect = makeSVG("rect", {x: positionX, y: 9, width: 200, height: 6});
        document.getElementById("svg_form_time").appendChild(rect);
        // <g><rect x="12" y="9" width="200" height="6"></rect></g>'
        var circle = makeSVG("circle", {
            cx: positionX,
            cy: 12,
            r: 12,
            width: positionX,
            height: 6
        });
        document.getElementById("svg_form_time").appendChild(circle);
    }

    var circle = makeSVG("circle", {
        cx: positionX + 200,
        cy: 12,
        r: 12,
        width: positionX,
        height: 6
    });
    document.getElementById("svg_form_time").appendChild(circle);

    $('#svg_form_time rect').css('fill', base_color);
    $('#svg_form_time circle').css('fill', base_color);
    $("circle:nth-of-type(1)").css("fill", active_color);


    $(document).on('click', '.button', function (e) {
        e.preventDefault();
        var id = $(this).attr("id");
        $("#svg_form_time rect").css("fill", active_color);
        $("#svg_form_time circle").css("fill", active_color);
        if (id == "next") {
            $("#prev").removeClass("disabled");
            if (child >= length) {
                $(this).addClass("disabled");
                $('#submit').removeClass("disabled");
            }
            if (child <= length) {
                child++;
            }
        } else if (id == "prev") {
            $("#next").removeClass("disabled");
            $('#submit').addClass("disabled");
            if (child <= 2) {
                $(this).addClass("disabled");
            }
            if (child > 1) {
                child--;
            }
        }
        var circle_child = child + 1;
        $("#svg_form_time rect:nth-of-type(n + " + child + ")").css(
                "fill",
                base_color
                );
        $("#svg_form_time circle:nth-of-type(n + " + circle_child + ")").css(
                "fill",
                base_color
                );
        var currentSection = $("section:nth-of-type(" + child + ")");
        currentSection.fadeIn();
        currentSection.css('transform', 'translateX(0)');
        currentSection.prevAll('section').css('transform', 'translateX(-100px)');
        currentSection.nextAll('section').css('transform', 'translateX(100px)');
        $('section').not(currentSection).hide();
    });

    $(document).on('change', '.inputGroupFile01', function (e) {
        console.log(e);
        if (e.target.files != null && e.target.files.length > 0) {
            var file = e.target.files[0];
            var mb = ((file.size / 1024) / 1024);
            if (file.type == "application/pdf") {
                if (mb < 15) {
                    $(this).next().find('span').text(e.target.files.length + " files");
                } else {
                    Swal.fire({
                        title: 'File Error!',
                        text: 'Uploaded File should be less than 15 mb',
                        icon: 'error',
                        confirmButtonText: 'Okay'
                    });
                }
            } else {
                Swal.fire({
                    title: 'File Error!',
                    text: 'Uploaded File should be a pdf',
                    icon: 'error',
                    confirmButtonText: 'Cool'
                })
            }
        }
    });
    $(document).on('change', '#inputGroupFile02', function (e) {
        console.log(e);
        if (e.target.files != null && e.target.files.length > 0) {
            var file = e.target.files[0];
            var mb = ((file.size / 1024) / 1024);
            if (file.type == "application/pdf") {
                if (mb < 15) {
                    $(this).next().text(file.name);
                } else {
                    Swal.fire({
                        title: 'File Error!',
                        text: 'Uploaded File should be less than 15 mb',
                        icon: 'error',
                        confirmButtonText: 'Okay'
                    });
                }
            } else {
                Swal.fire({
                    title: 'File Error!',
                    text: 'Uploaded File should be a pdf',
                    icon: 'error',
                    confirmButtonText: 'Okay'
                });
            }
        }
    });
    $(document).on('change', '#inputGroupFile03', function (e) {
        console.log(e);
        if (e.target.files != null && e.target.files.length > 0) {
            var file = e.target.files[0];
            var mb = ((file.size / 1024) / 1024);
            if (file.type == "image/jpeg") {
                if (mb < 15) {
                    $(this).next().text(file.name);
                } else {
                    Swal.fire({
                        title: 'File Error!',
                        text: 'Uploaded File should be less than 15 mb',
                        icon: 'error',
                        confirmButtonText: 'Okay'
                    });
                }
            } else {
                Swal.fire({
                    title: 'File Error!',
                    text: 'Uploaded File should be a pdf',
                    icon: 'error',
                    confirmButtonText: 'Cool'
                })
            }
        }
    });

});