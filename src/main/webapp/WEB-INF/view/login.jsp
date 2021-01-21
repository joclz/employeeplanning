<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mitarbeiter-Planung</title>
</head>
<body>
<h1>Login</h1>
<%--suppress HtmlUnknownTarget --%>
<form name='f' action="login" method='POST'>
    <table>
        <tr>
            <td><label for="username">User</label></td>
            <td><input id="username" type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td><label for="password">Password</label></td>
            <td><input id="password" type='password' name='password'/></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="submit"/></td>
        </tr>
    </table>
    <sec:csrfInput/>
</form>
</body>
</html>