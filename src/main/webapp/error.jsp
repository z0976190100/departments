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

<div class="container center">
    <h2><%= response.getStatus()%></h2>
    <h1>Error</h1>
    <h3><%= exception.getMessage()%></h3>
</div>
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
        </div>
    </div>
</footer>
</body>
</html>
