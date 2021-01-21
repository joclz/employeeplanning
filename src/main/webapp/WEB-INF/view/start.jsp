<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mitarbeiter-Planung</title>
</head>
<body>
<h1>Mitarbeiter-Planung</h1>
<table>
    <tr>
        <td> <form action="<%=request.getContextPath()%>/goToMitarbeiter" method="GET">
            <button>Mitarbeiter</button></form>
        </td>
        <td> <form action="<%=request.getContextPath()%>/goToMitarbeiterVertrieb" method="GET">
            <button>Mitarbeiter Vertrieb</button></form>
        </td>
        <td> <form action="<%=request.getContextPath()%>/goToEinsatz" method="GET">
            <button>Einsatz</button></form>
        </td>
        <td> <form action="logout" method="POST">
            <button>Logout</button>
            <sec:csrfInput/>
        </form>
        </td>
    </tr>
</table>

</body>
</html>