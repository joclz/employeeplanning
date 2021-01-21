<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mitarbeiter-Planung</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Mitarbeiter-Planung (Mitarbeiter Vertrieb)</h1>
<table>
    <tr>
        <td>
            <form action="<%=request.getContextPath()%>/goToStart" method="GET">
                <button>Zur&uuml;ck</button>
            </form>
        </td>
        <td>
            <form action="logout" method="POST">
                <button>Logout</button>
                <sec:csrfInput/>
            </form>
        </td>
    </tr>
</table>

<hr/>
<form action="<%=request.getContextPath()%>/listMitarbeiterVertrieb" method="GET">
    <button>Liste aller Mitarbeiter Vertrieb</button>
</form>

<hr/>
<form action="<%=request.getContextPath()%>/addMitarbeiterVertrieb" method="POST" content="">
    <table class="editDataTable">
        <tr>
            <td><label for="vorname">Vorname:</label></td>
            <td><input id="vorname" name="vorname"></td>
        </tr>
        <tr>
            <td><label for="name">Name:</label></td>
            <td><input id="name" name="name"></td>
        </tr>
        <tr>
            <td>
                <button>Mitarbeiter Vertrieb Hinzuf&uuml;gen</button>
            </td>
        </tr>
    </table>
    <sec:csrfInput/>
</form>
<hr/>
<form action="<%=request.getContextPath()%>/deleteMitarbeiterVertrieb" method="POST">
    <input title="Mitarbeiter-Id" type=number step=1 name="mitarbeiterVertriebId">
    <button>L&ouml;sche Mitarbeiter Vertrieb (mit ID)</button>
    <sec:csrfInput/>
</form>
</body>
</html>