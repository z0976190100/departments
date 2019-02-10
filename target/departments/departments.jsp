<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.z0976190100.departments.app_constants.General" %>
<%@ page import="com.z0976190100.departments.app_constants.ParameterNames" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
    <title>Departments</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="#" class="brand-logo">gh</a>
        <ul class="right">
            <li><a class="waves-effect waves-light light-green btn" href=<%= General.WELCOME_PAGE_JSP%>>< Welcome Page </a>
            </li>
        </ul>
    </div>
</nav>
<div class="container center">
    <h4>Departments Page</h4>
    <div class="row">
        <div class="col s6 offset-s3">
            <table class="highlight centered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty departments_list}">
                    <tr>
                        <td colspan="5">
                            No Departments yet... Sad...
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="department" items="${departments_list}">
                    <tr>
                        <td><c:out value="${department.getId()}"/></td>
                        <td><c:out value="${department.getTitle()}"/></td>
                        <td class="right">
                               <div class="col s4 center-align no-pad-bot"><form class="inline"><a class="waves-effect waves-light blue-grey btn"
                               title="To list of Employees of this Department."
                               href="${department.getId()}/employees"
                            >
                                Employees
                            </a></form></div>
                           <div class="col s3 center-align offset-s1"> <form class="inline"><a class="waves-effect waves-light blue-grey btn"
                               title="Smack to edit this Department."
                               href="departments/${department.getId()}/update"
                            >
                                Edit
                            </a>
                           </form>
                           </div>
                            <div class="col s4 center-align"><a class="waves-effect waves-light red btn "
                               title="Delete this Department."
                               href="departments/${department.getId()}/delete"
                            >
                                Delete
                            </a></div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="section">
        <div class="row">
            <div class="col s6 offset-s3 right-align">
                <a class="btn-floating btn-large waves-effect waves-light blue-grey modal-trigger" title="Add Department" data-target="add_form">
                    <i class="material-icons">add</i>
                </a>
            </div>
        </div>
    </div>
    <div class="section">
        <ul class="pagination">
            <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
            <li class="active light-blue"><a href="#!">1</a></li>
            <li class="waves-effect"><a href="#!">2</a></li>
            <li class="waves-effect"><a href="#!">3</a></li>
            <li class="waves-effect"><a href="#!">4</a></li>
            <li class="waves-effect"><a href="#!">5</a></li>
            <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
        </ul>
    </div>
</div>
<footer class="page-footer light-green">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <%--footer content--%>
            </div>
            <div class="col l4 offset-l2 s12">
                <%--<h5 class="white-text">Links</h5>--%>
                <%--<ul>--%>
                <%--<li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>--%>
                <%--</ul>--%>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2019 good habit. get another one is us.
            <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
        </div>
    </div>
</footer>
<script src="materialize/js/materialize.min.js"></script>
<script src="js/index.js"></script>
<!-- Notification pop-up -->
<c:if test="${not empty errors}">
    <script>
        notification("${errors}");
    </script>
</c:if>
<!-- Modal Add Department Form -->
<div id="add_form" class="modal">
    <div class="modal-content">
        <h4>Add Department</h4>
        <div class="row">
            <form action="/departments" method="post" class="col s12">
                <div class="row">
                    <div class="input-field col s12">
                        <input name=<%= ParameterNames.DEPARTMENT_TITLE_PARAM %> id="department_title" type="text" class="validate" required>
                        <label for=<%= ParameterNames.DEPARTMENT_TITLE_PARAM %>>Title</label>
                        <span class="helper-text" data-error="${errors}" data-success="Well done!">Feel free to type letters and numbers, even do hyphens.
                            And that's all. No other symbols are permitted! 22 characters, not more.</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#" class="modal-close waves-effect waves-green btn-flat ">Cancel</a>
                    <button type="submit" class="waves-effect waves-green btn-flat green-text">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

</div>

</body>

</html>
