
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
    modalTitle.innerText = title + " Department.";

    var form = byId("delete-confirmation-modal-form");
    form.action = "departments/delete/" + id;

    console.log(form.action);

    var input = byId("delete-confirmation-modal-input");
    input.value = id;

    console.log(id, title);



}

function notification(text) {
    M.toast({html: text, classes: "red"});
};
