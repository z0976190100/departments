(function () {
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems);

        // set pointer of pagination to actual page
        // set disabled for page-switcher if first or last
        //
    });
})();

function byId(id) {
    return document.getElementById(id);
}

function setPaginationPointer(actualPage, pages) {

    for (var i = 1; i <= pages; i++) {
        var indicator = byId("page-indicator-" + i);
        console.log(indicator);
        i == actualPage ? indicator.className = "active blue" : indicator.className = "waves-effect";
    }

    actualPage == 1 ? byId("page-back-switcher").className = "disabled" : byId("page-back-switcher").className = "waves-effect";

    actualPage == pages ?  byId("page-forward-switcher").className = "disabled" : byId("page-forward-switcher").className = "waves-effect";
}

function setDeleteModalValues(id, title) {

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

