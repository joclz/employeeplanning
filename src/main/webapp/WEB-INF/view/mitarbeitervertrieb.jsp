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
            <form action="/goToStart" method="GET">
                <button>Zur&uuml;ck</button>
            </form>
        </td>
        <td>
            <form action="/logout" method="GET">
                <button>Logout</button>
            </form>
        </td>
    </tr>
</table>

<hr/>
<form action="/listMitarbeiterVertrieb" method="GET">
    <button>Liste aller Mitarbeiter Vertrieb</button>
</form>

<hr/>
<form action="/addMitarbeiterVertrieb" method="POST" content="">
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
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
<hr/>
<form action="/deleteMitarbeiterVertrieb" method="POST">
    <input title="Mitarbeiter-Id" type=number step=1 name="mitarbeiterVertriebId">
    <button>L&ouml;sche Mitarbeiter Vertrieb (mit ID)</button>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
</body>
</html>