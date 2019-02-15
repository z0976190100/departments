<%@ page import="com.z0976190100.departments.app_constants.GeneralConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>D${department.getTitle()}</title>
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
            <li><a class="waves-effect waves-light light-green btn" href="departments?command=get_all">< Departments </a>
            </li>
        </ul>
    </div>
</nav>
<div class="container center">
    <div class="section"></div>
    <h4 class="blue-grey-text"><strong>DEPARTMENT of ${department.getTitle()}</strong></h4>
    <h6 class="blue-grey-text">EMPLOYEES list</h6>
    <div class="section"></div>
    <div class="row">
        <div class="col s6 offset-s3">
            <table class="highlight centered">
                <thead class="blue-grey-text">
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
                            No Employees yet... Sad...
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="department" items="${departments_list}">
                    <tr>
                        <td><c:out value="${department.getId()}"/></td>
                        <td><c:out value="${department.getTitle()}"/></td>
                        <td class="right">
                            <button class="waves-effect waves-light blue-grey btn modal-trigger s12"
                                    title="Smack to edit this Department."
                                    onclick="setEditModalValues('${department.getId()}', '${department.getTitle()}')"
                                    data-target="add-edit-modal"
                            >
                                Edit
                            </button>
                            <button class="waves-effect waves-light red btn modal-trigger s12"
                                    title="Delete this Department."
                                    onclick="setDeleteModalValues('${department.getId()}', '${department.getTitle()}')"
                                    data-target="delete-confirmation-modal"
                            >
                                Delete
                            </button>
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
                <button class="btn-floating btn-large waves-effect waves-light blue-grey modal-trigger"
                        title="Add Department"
                        onclick="setSaveModalValues()"
                        data-target="add-edit-modal">
                    <i class="material-icons">add</i>
                </button>
            </div>
        </div>
    </div>
    <div class="section">
        <ul class="pagination">
            <li id="page-back-switcher" class="disabled"><a href="/departments?page=${page - 1}"><i class="material-icons">chevron_left</i></a></li>
            <c:forEach var="page" begin="1" end="${pages}">
                <li id="page-indicator-${page}" class="waves-effect"><a href="/departments?page=${page}">${page}</a></li>
            </c:forEach>
            <li id="page-forward-switcher" class="waves-effect"><a href="/departments?page=${page + 1}"><i class="material-icons">chevron_right</i></a></li>
        </ul>
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
<c:if test="${not empty errors}">
    <script>
        notification("${errors}");
    </script>
</c:if>
</div>
</body>
</html>
