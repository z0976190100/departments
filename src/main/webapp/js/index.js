
(function () {
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems);
    });
})();


function setDeleteModalValues(id, title) {

    function byId(id) {
        return document.getElementById(id);
    }

    var modalTitle = byId("delete-confirmation-modal-title");
    modalTitle.innerText = title + " Department";

    var form = byId("delete-confirmation-modal-form");
    form.action = "departments/delete/" + id;

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
    form.action = "departments/update/" + id;

    var input = byId("add-edit-modal-input");
    input.click = true;
    input.focus = true;
    input.value = title;
}

function setSaveModalValues() {

    function byId(id) {
        return document.getElementById(id);
    }

    var modalTitle = byId("add-edit-modal-title");
    modalTitle.innerText = "Add Department";

    var form = byId("add-edit-modal-form");
    form.action = "departments";
}

function notification(text) {
    M.toast({html: text, classes: "red"});
};
