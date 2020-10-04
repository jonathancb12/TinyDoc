$(document).ready(function () {
    // $("#choose").onchange(function () {
    //     $("#btn-aplicar").fadeIn("slow");
    // });

    $("#choose").onchange = function (event) {
        var target = event.target;
        if (target.value.length > 0) {
            $("#btn-aplicar").fadeIn("slow");
        }
    }
});
