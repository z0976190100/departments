
(function () {
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems);
    });
})();


function notification(text) {
    M.toast({html: text, classes: "red"});
};
