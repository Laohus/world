$(document).ready(function() {

    $("#homeback").click(function() {

        $(location).prop("href","/Welcome")
        return true;
    });
    $("#article").click(function() {

        $(location).prop("href","/Blog")
        return true;
    });



});
