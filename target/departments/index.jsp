<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.z0976190100.departments.app_constants.URLs" %>
<%@ page import="com.z0976190100.departments.app_constants.General" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
    <title>Welcome Page</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="#" class="brand-logo">gh</a>
        <ul class="right">
            <li><a class="waves-effect waves-light btn" href=<%= General.DEPARTMENTS_JSP%>>Departments ></a></li>
        </ul>
    </div>
</nav>
<div class="container center">
    <h1>WELCOMEPAGE</h1>
</div>
<footer class="page-footer">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
               <%--footer content--%>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2019 good habit. another one is us.
        </div>
    </div>
</footer>
</body>
</html>
