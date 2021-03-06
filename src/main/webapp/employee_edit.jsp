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
        <a id="logo-container" href="http://mobile.azino-77.ru/" class="brand-logo" >gh</a>
        <ul class="right">
            <li><a class="waves-effect waves-light light-green btn"
                   href="departments?command=get&id=${employee == null ? param.department_id : employee.getDepartmentID()}">
                < ${employee == null ? param.department_id : employee.getDepartmentID()} Department
            </a>
            </li>
        </ul>
    </div>
</nav>
<div class="container with-height">
    <div class="section"></div>
    <h4 class="blue-grey-text center"><strong>EDIT EMPLOYEE</strong></h4>
    <h6 class="blue-grey-text center">${employee == null ? param.email : employee.getEmail()}</h6>
    <div class="section"></div>
    <div class="row">
        <div class="col s6 offset-s3">
            <form method="post" action="/employees?command=update">
                <div class="row">
                    <div class="input-field col s12">
                        <input name="email"
                               type="hidden"
                               value="${employee == null ? param.email : employee.getEmail()}"
                        >
                        <input name="id"
                               type="hidden"
                               value="${employee == null ? param.id : employee.getId()}"
                        >
                        <input name="department_id"
                               type="hidden"
                               value="${employee == null ? param.department_id : employee.getDepartmentID()}"
                        >
                        <c:if test="${not empty errorsList}">
                            <c:if test="${not empty errorsList.get(\"name\")}">
                                <i class="material-icons prefix red-text">error</i>
                            </c:if>
                        </c:if>
                        <input placeholder="Name" name="name" id="name" type="text" class="validate"
                               value="${employee == null ? param.name : employee.getName()}">
                        <span class="helper-text"
                              data-error="Field can contain letters, hyphen and '. Cannot be empty."
                              data-success="Well done!"
                        >
                            Field can contain letters, hyphen and '. Cannot be empty.
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <c:if test="${not empty errorsList}">
                            <c:if test="${not empty errorsList.get(\"age\")}">
                                <i class="material-icons prefix red-text">error</i>
                            </c:if>
                        </c:if>
                        <input placeholder="Age" name="age" id="age" type="text" class="validate"
                               value="${employee == null ? param.age : employee.getAge()}">
                        <span class="helper-text"
                              data-error="Field can contain numbers only. Cannot be empty."
                              data-success="Well done!"
                        >
                           Field can contain numbers only. Cannot be empty.
                        </span>
                    </div>
                    <div class="input-field col s6">
                        <c:if test="${not empty errorsList}">
                            <c:if test="${not empty errorsList.get(\"birth_date\")}">
                                <i class="material-icons prefix red-text">error</i>
                            </c:if>
                        </c:if>
                        <input placeholder="Date of Birth" name="birth_date" id="birth_date" type="text"
                               class="datepicker"
                               value="${employee == null ? param.birth_date : employee.getBirthDate()}"
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
            notification("${error}");
        </script>
    </c:forEach>
</c:if>
<script>
    datePickerInit('${min_date}', '${max_date}');
</script>
</div>
</body>
</html>
