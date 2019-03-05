<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="http://mobile.azino-77.ru/" class="brand-logo">gh</a>
    </div>
</nav>
<div class="section"></div>
<div class="section"></div>
<div class="section"></div>
<div class="section"></div>
<div class="container center grey-text">
    <h3><%= response.getStatus()%>
    </h3>
    <h1><strong>Error</strong></h1>
    <div class="section"></div>
    <h3><%= exception.getMessage()%>
    </h3>
</div>
<div class="section"></div>
<div class="section"></div>
<div class="section"></div>
<div class="section"></div>
<footer class="page-footer light-green">
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
            <a class="grey-text text-lighten-4 right" href="http://github.com/z0976190100">My Github</a>
        </div>
    </div>
</footer>
</body>
</html>
