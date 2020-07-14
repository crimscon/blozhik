function GetButtons() {

    return {

        buttonSelf: $(`#dropdownMenuButton`),
        buttonFile: $(`.custom-file`).find("label:first"),
        buttonSubmit: $(`.custom-file`).next(),

        modal: $(`#Message`),
        header: $(`.modal-header`),

    }

}

function _changeColorMake(color) {

    changeColor.buttons = new function () {

        this._ = color || "primary";

        this.buttonSelf = "btn-outline-" + this._;
        this.buttonFile = this.buttonSelf;
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