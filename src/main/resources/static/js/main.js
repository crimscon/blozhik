class Elements {
    constructor() {
        this.domElements = {
            modal: document.getElementById('Message'),
            header: document.querySelector('.modal-header'),
            footer: document.querySelector('.modal-footer')
        };

        this.buttons = {
            self: this.domElements.footer.querySelector('#dropdownMenuButton'),
            file: document.querySelector('.buttonFile button:first-child'),
            label: this.domElements.footer.querySelector('#send'),
            submit: this.domElements.footer.querySelector('#submit')
        };
    }

    removeClassAndAdd(element, className) {
        element.className = '';
        element.classList.add(className);
    }

    changeColor(color) {
        const newColor = `btn-outline-${color}`;
        Object.values(this.buttons).forEach(button => this.removeClassAndAdd(button, newColor));
        this.removeClassAndAdd(this.domElements.modal, `border-${color}`);
        this.removeClassAndAdd(this.domElements.header, `alert-${color}`);
        this.removeClassAndAdd(this.buttons.label, `border-${color}`);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const elements = new Elements();
    elements.changeColor(getColor());

    document.querySelectorAll('.dropdown-menu input').forEach(item =>
        item.addEventListener('click', function () {
            elements.changeColor(this.getAttribute('id').toLowerCase());
        })
    );
    applyMaskToInput('#newTel');
    customFileHandler('#customFile');
    customFileButtonClick('.buttonFile');
});

function getColor() {
    const classes = document.getElementById('dropdownMenuButton').getAttribute('class')
    const classList = classes.split(/\s+/);
    const classAttributes = classList[classList.length - 1].split('-');
    return classes ? classAttributes[classAttributes.length - 1] : 'primary';
}

function applyMaskToInput(inputSelector) {
    [].forEach.call(document.querySelectorAll(inputSelector), function (input) {
        input.addEventListener("input", processInputEvent, false);
        input.addEventListener("focus", processInputEvent, false);
        input.addEventListener("blur", processInputEvent, false);
        input.addEventListener("keydown", processInputEvent, false)
    });

    const processInputEvent = (event) => {
        const KEY_CODE = event.keyCode,
            POSITION = event.target.selectionStart,
            INPUT_MATRIX = "+7 (___) ___ ____";

        let keyCode = KEY_CODE || null,
            value = event.target.value.replace(/\D/g, ""),
            def = INPUT_MATRIX.replace(/\D/g, ""),
            i = 0;
        if (POSITION < 3) {
            event.preventDefault();
        }

        let newValue = INPUT_MATRIX.replace(
            /[_\d]/g,
            a => i < value.length ? value.charAt(i++) || def.charAt(i) : a
        );
        let index = newValue.indexOf("_");

        if (index !== -1) {
            if (index < 5) {
                index = 3;
            }
            newValue = newValue.slice(0, index);
        }

        let regex = createRegExp(INPUT_MATRIX, event.target.value);
        if (!regex.test(event.target.value) || event.target.value.length < 5 || keyCode > 47 && keyCode < 58) {
            event.target.value = newValue;
        }

        if (event.type === "blur" && event.target.value.length < 5) {
            event.target.value = "";
        }
    }

    const createRegExp = (matrix, inputValue) => {
        let value = matrix.substring(0, inputValue.length)
            .replace(/_+/g, a => "\\d{1," + a.length + "}")
            .replace(/[+()]/g, "\\$&");
        return new RegExp("^" + value + "$");
    }
}

function customFileHandler(fileSelector) {
    const file = document.querySelector(fileSelector);

    file.addEventListener('change', function () {
        let fileName = this.value.split('\\').pop();
        let nextElement = this.nextElementSibling;

        if (nextElement && nextElement.classList.contains('custom-file-label')) {
            nextElement.classList.add("selected");
            nextElement.innerHTML = fileName.toString();
        }
    });
}

function customFileButtonClick(buttonSelector) {
    const button = document.querySelector(buttonSelector).querySelector('button:first-child');
    button.addEventListener('click', function () {
        const sendButton = document.getElementById('send');
        sendButton.click();
    });
}