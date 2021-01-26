<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mitarbeiter-Planung</title>
</head>
<body>
<h1>Mitarbeiter-Planung (Mitarbeiter)</h1>
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
<form action="<%=request.getContextPath()%>/listMitarbeiter" method="GET">
    <button>Liste aller Mitarbeiter</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/getLastEndDateForMitarbeiter" method="GET">
    <input title="Mitarbeiter-Id" type=number step=1 name="mitarbeiterId">
    <button>Wie lange ist Mitarbeiter (mit ID) noch im Einsatz</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/getChanceForMitarbeiter" method="GET">
    <input title="Mitarbeiter-Id" type=number step=1 name="mitarbeiterId">
    <button>Wie hoch ist Chance auf Verl&auml;ngerung (f&uuml;r Mitarbeiter mit ID)</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/listMitarbeiterBank" method="GET">
    <button>Liste aller Mitarbeiter auf der Bank</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/countMitarbeiterImEinsatz" method="GET">
    <select title="Mitarbeiter-Status" name="mitarbeiterStatus">
        <option value=""></option>
        <option value="ANGESTELLT">ANGESTELLT</option>
        <option value="SUBUNTERNEHMER">SUBUNTERNEHMER</option>
    </select>
    <button>Wieviele Mitarbeiter bzw. Subunternehmer sind im Einsatz</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/listMitarbeiterInternBank" method="GET">
    <button>Welche internen Mitarbeiter sind aktuell nicht im Einsatz</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/getDeckungsbeitrag" method="GET">
    <button>Welchen Umsatz bzw. Deckungsbeitrag haben wir aktuell</button>
</form>
<br>
<form action="/getDeckungsbeitragJahr" method="GET">
    <button>Welchen Umsatz bzw. Deckungsbeitrag haben wir übers Jahr</button>
</form>
<!--
<hr>
<form action="/addMitarbeiterMitEinzelnenWerten" method="POST">
    <div>
        <label for="mVorname">Vorname:</label>
        <input name="vorname" id="mVorname">
    </div>
    <div>
        <label for="mName">Name:</label>
        <input name="name" id="mName" value="">
    </div>
    <div>
        <label for="mStatus">Status:</label>
        <select name="status" id="mStatus">
            <option value="ANGESTELLT">ANGESTELLT</option>
            <option value="SUBUNTERNEHMER">SUBUNTERNEHMER</option>
        </select>
    </div>
    <div>
        <label for="mUnit">Unit:</label>
        <select name="unit" id="mUnit">
            <option value="FACTORY_MUENCHEN">FACTORY_MUENCHEN</option>
            <option value="FACTORY_NUERNBERG">FACTORY_NUERNBERG</option>
        </select>
    </div>
    <div>
        <label for="mStundensatzEK">Stundensatz EK:</label>
        <input type=number step=0.01 name="stundensatzEK" id="mStundensatzEK">
    </div>
    <div>
        <button>Mitarbeiter Hinzufügen</button>
    </div>
</form>
-->

<hr/>
<form action="<%=request.getContextPath()%>/addMitarbeiter" method="POST" content="">
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
            <td><label for="mitarbeiterStatus">Status:</label></td>
            <td><select id="mitarbeiterStatus" name="mitarbeiterStatus">
                <option value="ANGESTELLT">ANGESTELLT</option>
                <option value="SUBUNTERNEHMER">SUBUNTERNEHMER</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="unit">Unit:</label></td>
            <td><select id="unit" name="mitarbeiterUnit">
                <option value="FACTORY_MUENCHEN">FACTORY_MUENCHEN</option>
                <option value="FACTORY_NUERNBERG">FACTORY_NUERNBERG</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="stundensatzEK">Stundensatz EK:</label></td>
            <td><input id="stundensatzEK" type=number step=0.01 name="stundensatzEK"></td>
        </tr>
        <tr>
            <td>
                <button>Mitarbeiter Hinzuf&uuml;gen</button>
            </td>
        </tr>
    </table>
    <sec:csrfInput/>
</form>
<hr/>
<form action="<%=request.getContextPath()%>/deleteMitarbeiter" method="POST">
    <input title="Mitarbeiter-Id" type=number step=1 name="mitarbeiterId">
    <button>L&ouml;sche Mitarbeiter (mit ID)</button>
    <sec:csrfInput/>
</form>
</body>
</html>