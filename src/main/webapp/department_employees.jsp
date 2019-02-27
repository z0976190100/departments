<%@ page import="com.z0976190100.departments.app_constants.GeneralConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${department.getTitle()}</title>
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
        <div class="col s8 offset-s2">
            <table class="highlight centered">
                <thead class="blue-grey-text">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Age</th>
                    <th>Birth Date</th>
                    <th colspan="2"> Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty employees_list}">
                    <tr>
                        <td colspan="6">
                            No Employees yet... Sad...
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="employee" items="${employees_list}">
                    <tr>
                        <td><c:out value="${employee.getId()}"/></td>
                        <td><c:out value="${employee.getName()}"/></td>
                        <td><c:out value="${employee.getEmail()}"/></td>
                        <td><c:out value="${employee.getAge()}"/></td>
                        <td><c:out value="${employee.getBirthDate()}"/></td>
                        <td class="right">
                            <a href="/employees?command=get&id=${employee.getId()}"
                                    class="waves-effect waves-light blue-grey btn modal-trigger s12"
                                    title="Smack to edit this Employee."
                            >
                                Edit
                            </a>
                            <button class="waves-effect waves-light red btn modal-trigger s12"
                                    title="Delete this Employee."
                                    onclick="setDeleteModalValues('<%= GeneralConstants.EMPLOYEES_URI%>', 'Employee', '${employee.getId()}', '${employee.getEmail()}')"
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
            <div class="col s8 offset-s2 right-align">
                <a href="employees?command=get&department_id=${department.getId()}" class="btn-floating btn-large waves-effect waves-light blue-grey modal-trigger"
                        title="Add Employee"
                >
                    <i class="material-icons">add</i>
                </a>
            </div>
        </div>
    </div>
    <div class="section">
        <ul class="pagination">
            <li id="page-back-switcher" class="disabled"><a href="departments?command=get&id=${department.getId()}&page=${page - 1}"><i class="material-icons">chevron_left</i></a></li>
            <c:forEach var="page" begin="1" end="${pages}">
                <li id="page-indicator-${page}" class="waves-effect"><a href="departments?command=get&id=${department.getId()}&page=${page}">${page}</a></li>
            </c:forEach>
            <li id="page-forward-switcher" class="waves-effect"><a href="departments?command=get&id=${department.getId()}& page=${page + 1}"><i class="material-icons">chevron_right</i></a></li>
        </ul>
    </div>
</div>
<div class="section"></div>
<footer class="page-footer fixed-footer light-green">
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
    <c:forEach var="error" items="${errorsList}" >
    <script>
        notification("${error.getMessage()}");
    </script>
    </c:forEach>
</c:if>
</div>
<!-- Modal Add Edit Employee Form -->
<div id="add-edit-modal" class="modal">
    <div class="modal-content">
        <h4 class="blue-grey-text" id="add-edit-modal-title">Add Department</h4>
        <div class="row">
            <form id="add-edit-modal-form" method="post" class="col s12">
                <div class="row">
                    <div  class="input-field col s12">
                        <input name=<%= GeneralConstants.EMAIL_PARAM %> id="add-edit-modal-input"
                               type="text"
                               class="validate"
                               value="${email}" required
                               placeholder="Email"
                        />
                        <input id="add-edit-modal-command-input"
                               name="command"
                               type="hidden"
                        />
                        <input id="add-edit-modal-id-input"
                               name="department_id"
                               type="hidden"
                               value="${department.getId()}"
                        />
                        <span class="helper-text"
                              data-error="Feel free to type LETTERS and NUMBERS, even do hyphens.
                            No other symbols are permitted!"
                              data-success="Well done!"
                        >
                            Feel free to type letters and numbers, even do hyphens.
                            And that's all. No other symbols are permitted! 22 characters, not more.
                        </span>
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
<!-- Modal Delete Employee Confirmation Form -->
<div id="delete-confirmation-modal" class="modal">
    <div class="modal-content">
        <h4 class="blue-grey-text" id="delete-confirmation-modal-title">Delete Modal</h4>
        <h6 class="red-text">You now completely deleting this record.</h6>
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
