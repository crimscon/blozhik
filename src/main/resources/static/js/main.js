function GetButtons() {

    return {

        buttonSelf: $(`#dropdownMenuButton`),
        buttonFile: $(`.buttonFile`).find("button:first"),
        fileLabel: $(`#send`),
        buttonSubmit: $(`#submit`),

        modal: $(`#Message`),
        header: $(`.modal-header`),

    }

}

function _changeColorMake(color) {

    changeColor.buttons = new function () {

        this._ = color || "primary";

        this.buttonSelf = "btn-outline-" + this._;
        this.buttonFile = this.buttonSelf;
        this.fileLabel = "border-" + this._;
        this.buttonSubmit = this.buttonSelf;

        this.modal = "border-" + this._;
        this.header = "alert-" + this._;

    }

}

function changeColor() {

    const id = $(this).attr("id");

    const currentColor = id.toLowerCase();

    const elements = GetButtons();

    for (const element in elements) {

        elements[element].removeClass(changeColor.buttons[element]);

    }

    _changeColorMake(currentColor);

    for (const element in elements) {

        elements[element].addClass(changeColor.buttons[element]);

    }

}

_changeColorMake();

document.addEventListener("DOMContentLoaded", function () {

    $(".dropdown-menu").children("input").click(changeColor);

})

window.addEventListener("DOMContentLoaded", function () {
    [].forEach.call(document.querySelectorAll('.tel'), function (input) {
        var keyCode;

        function mask(event) {
            event.keyCode && (keyCode = event.keyCode);
            var pos = this.selectionStart;
            if (pos < 3) event.preventDefault();
            var matrix = "+7 (___) ___ ____",
                i = 0,
                def = matrix.replace(/\D/g, ""),
                val = this.value.replace(/\D/g, ""),
                new_value = matrix.replace(/[_\d]/g, function (a) {
                    return i < val.length ? val.charAt(i++) || def.charAt(i) : a
                });
            i = new_value.indexOf("_");
            if (i !== -1) {
                i < 5 && (i = 3);
                new_value = new_value.slice(0, i)
            }
            var reg = matrix.substr(0, this.value.length).replace(/_+/g,
                function (a) {
                    return "\\d{1," + a.length + "}"
                }).replace(/[+()]/g, "\\$&");
            reg = new RegExp("^" + reg + "$");
            if (!reg.test(this.value) || this.value.length < 5 || keyCode > 47 && keyCode < 58) this.value = new_value;
            if (event.type === "blur" && this.value.length < 5) this.value = ""
        }

        input.addEventListener("input", mask, false);
        input.addEventListener("focus", mask, false);
        input.addEventListener("blur", mask, false);
        input.addEventListener("keydown", mask, false)

    });

});

$('.custom-file-input').on('change', function() {
    let fileName = $(this).val().split('\\').pop();
    $(this).next('.custom-file-label').addClass("selected").html(fileName);
});

$(`.buttonFile`).find("button:first").click(function(){
    $('#send').click();
});