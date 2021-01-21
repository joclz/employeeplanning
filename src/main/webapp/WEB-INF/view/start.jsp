<%--suppress HtmlUnknownTarget --%>
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
        <td> <form action="<%=request.getContextPath()%>/logout" method="GET">
            <button>Logout</button>
        </form>
        </td>
    </tr>
</table>

</body>
</html>