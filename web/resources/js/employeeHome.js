$(document).ready(function () {

    $(document).on('click', '#verifyEmail', function (e) {
        e.preventDefault();
        Swal.showLoading();
        $.ajax({
            method: "GET",
            url: "/pooBabySitting/sendOTP",
            dataType: 'JSON'
        }).done(function (data) {
            console.log(data);

            Swal.fire({
                title: 'Enter OTP',
                input: 'text',
                inputAttributes: {
                    autocapitalize: 'off',
                    required: 'true'
                },
                validationMessage: 'Enter OTP to proceed!',
                showCancelButton: true,
                footer: '<b style="color:light-blue;">OTP is sent on the registered email.</b>',
                confirmButtonText: 'Confirm',
                showLoaderOnConfirm: true,
                preConfirm: (otp) => {
                    return fetch(`/pooBabySitting/sendOTP?sentOTP=${otp}`, {
                        method: 'POST'})
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error(response.statusText)
                                }
                                return response.json()
                            })
                            .catch(error => {
                                Swal.showValidationMessage(
                                        `Request failed: ${error}`
                                        )
                            })
                },
                allowOutsideClick: () => !Swal.isLoading()
            }).then((result) => {
                if (result.isConfirmed) {
                    console.log(result);
                    Swal.fire({
                        icon: 'success',
                        title: result.value.response,
                    });
                    $('.TextBox>p').remove();S
                }
            });

        }).fail(function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus.reponseText());
        });
    });
});