<%@ page import="com.z0976190100.departments.app_constants.GeneralConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employee</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="#" class="brand-logo">gh</a>
        <ul class="right">
            <li><a class="waves-effect waves-light light-green btn"
                   href="departments?command=get&id=${param.department_id}">< ${param.department_id} Department </a>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="section"></div>
    <h4 class="blue-grey-text center"><strong>EMPLOYEE</strong></h4>
    <div class="section"></div>
    <div class="row">
        <div class="col s6 offset-s3">
            <form method="post" action="/employees?command=save">
                <div class="row">
                    <div class="input-field col s12">
                        <input name="department_id"
                               type="hidden"
                               value="${param.department_id}"
                        >
                        <input placeholder="Name" name="name" id="name" type="text" class="validate"
                               value="${param.name}">
                        <span class="helper-text"
                              data-error="Field can contain letters, hyphen and '. Cannot be empty."
                              data-success="Well done!"
                        >
                            Field can contain letters, hyphen and '. Cannot be empty.
                        </span>
                    </div>
                    <div class="input-field col s12">
                        <c:if test="${not empty errorsList}">
                            <i class="material-icons prefix red-text">error</i>
                        </c:if>
                        <input required placeholder="Email" name="email" id="email" type="email" class="validate"
                               value="${param.email}"
                        >
                        <span class="helper-text"
                              data-error="Provide valid email, please."
                              data-success="Well done!"
                        >
                            Enter email address.
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <input placeholder="Age" name="age" id="age" type="text" class="validate" value="${param.age}">
                        <span class="helper-text"
                              data-error="Field can contain numbers only. Cannot be empty."
                              data-success="Well done!"
                        >
                           Field can contain numbers only. Cannot be empty.
                        </span>
                    </div>
                    <div class="input-field col s6">
                        <input placeholder="Date of Birth" name="birth_date" id="birthdate" type="text"
                               class="datepicker"
                               value="${param.birth_date}"
                               min="${min_date}"
                               max="${max_date}"
                        >
                        <span class="helper-text"
                              data-error="Pick a date."
                              data-success="Well done!"
                        >
                            Pick a date.
                        </span>
                    </div>
                </div>
                <div class="row">
                    <button type="submit" class="waves-effect waves-green btn-flat green-text right">Save</button>
                    <button type="reset" class="waves-effect waves-green btn-flat red-text right">Reset</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="section"></div>
<footer class="page-footer light-green">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
            </div>
            <div class="col l4 offset-l2 s12">
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2019 good habit. here another one is us.
            <a class="grey-text text-lighten-4 right" href="http://github.com/z0976190100">My Github</a>
        </div>
    </div>
</footer>
<script src="materialize/js/materialize.min.js"></script>
<script src="js/index.js"></script>
<!-- set Pagination Pointer invocation -->
<script>
    setPaginationPointer(${page}, ${pages});
</script>
<!-- Notification pop-up -->
<c:if test="${not empty errorsList}">
    <c:forEach var="error" items="${errorsList}">
        <script>
            notification("${error.getMessage()}");
        </script>
    </c:forEach>
</c:if>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.datepicker');
        console.log(new Date('${max_date}'), new Date('${min_date}'));
        var opt = {
            yearRange: [new Date('${min_date}').getFullYear(), new Date('${max_date}').getFullYear()],
            defaultDate: new Date('${max_date}'),
            minDate: new Date('${min_date}'),
            maxDate: new Date('${max_date}')
        };
        var instances = M.Datepicker.init(elems, opt);
    });
</script>
</div>

<!-- Modal Delete Employee Confirmation Form -->
<div id="delete-confirmation-modal" class="modal">
    <div class="modal-content">
        <h4 class="blue-grey-text" id="delete-confirmation-modal-title">Delete Modal</h4>
        <h6 class="red-text">You now completely deleting us.</h6>
        <div class="row">
            <form id="delete-confirmation-modal-form" method="post" class="col s12">
                <div class="row">
                    <div class="input-field col s12">
                        <input name="department_id"
                               type="hidden"
                               value="${department.getId()}"
                        >
                        <input name="id"
                               type="hidden"
                               id="delete-confirmation-modal-input"
                        >
                        <input name="command"
                               value="delete"
                               type="hidden"
                               id="delete-confirmation-modal-command-input"
                        >
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#" class="modal-close waves-effect waves-green btn-flat ">Nope</a>
                    <button type="submit" class="waves-effect waves-green btn-flat green-text">Sure</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
