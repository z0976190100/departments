(function () {
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems);
    });

})();

function datePickerInit(minDate, maxDate) {
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.datepicker');
        var opt = {
            format: "yyyy-mm-dd",
            yearRange: [new Date(minDate).getFullYear(), new Date(maxDate).getFullYear()],
            defaultDate: new Date(maxDate),
            minDate: new Date(minDate),
            maxDate: new Date(maxDate)
        };
        var instances = M.Datepicker.init(elems, opt);
    });
}
// TODO: form reseting click-handler
 
function byId(id) {
    return document.getElementById(id);
}

function setPaginationPointer(actualPage, pages) {

    for (var i = 1; i <= pages; i++) {

        var indicator = byId("page-indicator-" + i);

        i == actualPage ? indicator.className = "active blue" : indicator.className = "waves-effect";
    }

    actualPage == 1 ? byId("page-back-switcher").className = "disabled" : byId("page-back-switcher").className = "waves-effect";

    actualPage == pages ?  byId("page-forward-switcher").className = "disabled" : byId("page-forward-switcher").className = "waves-effect";
}

function setDeleteModalValues(uri, entity, id, title) {

    console.log(uri, entity, id, title);
    var modalTitle = byId("delete-confirmation-modal-title");
    modalTitle.innerText = title + " " + entity;

    var form = byId("delete-confirmation-modal-form");
    form.action = uri;

    var input = byId("delete-confirmation-modal-input");
    input.value = id;
}

function setEditModalValues(id, title) {

    function byId(id) {
        return document.getElementById(id);
    }

    var modalTitle = byId("add-edit-modal-title");
    modalTitle.innerText = "Edit Department";

    var form = byId("add-edit-modal-form");
    form.action = "departments";

    var command = byId("add-edit-modal-command-input");
    command.value = "update";

    var idInput = byId("add-edit-modal-id-input");
    idInput.value = id;

    var input = byId("add-edit-modal-input");
    input.click = true;
    input.focus = true;
    input.value = title;
}

function setSaveModalValues(uri, entity) {

    function byId(id) {
        return document.getElementById(id);
    }

    var modalTitle = byId("add-edit-modal-title");
    modalTitle.innerText = "Add " + entity;

    var form = byId("add-edit-modal-form");
    form.action = uri;

    var command = byId("add-edit-modal-command-input");
    command.value = "save";
}

function notification(text) {
    M.toast({html: text, classes: "red"});
};

