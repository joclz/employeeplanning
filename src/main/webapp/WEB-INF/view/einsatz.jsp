<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mitarbeiter-Planung</title>
</head>
<body>
<h1>Mitarbeiter-Planung (Einsatz)</h1>
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
<form action="<%=request.getContextPath()%>/listEinsaetze" method="GET">
    <button>Liste aller Eins&auml;tze</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/findEinsaetzeByEinsatzStatus" method="GET">
    <select title="Status" name="status">
        <option value="NULL"></option>
        <option value="ANGEBOTEN">ANGEBOTEN</option>
        <option value="BEAUFTRAGT">BEAUFTRAGT</option>
        <option value="ABGELEHNT">ABGELEHNT</option>
    </select>
    <button>Liste der Eins&auml;tze mit Status</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/findEinsaetzeByMitarbeiterVertrieb" method="GET">
    <input title="Mitarbeiter" type=number step=1 name="mitarbeiterVertriebId">
    <button>Liste der Eins&auml;tze f&uuml;r Vertriebsmitarbeiter (ID)</button>
</form>
<br>
<form action="<%=request.getContextPath()%>/getEinsatzById" method="GET">
    <input title="Einsatz" type=number step=1 name="einsatzId">
    <button>Lade Einsatz mit ID</button>
</form>
<hr/>
<form action="<%=request.getContextPath()%>/findEinsaetzeBySuchkriterien" method="POST">
    <table class="editDataTable">
        <tr>
            <td><label for="mitarbeiterVertriebId_findEinsaetzeBySuchkriterien">Mitarbeiter Vertrieb (ID):</label></td>
            <td><input id="mitarbeiterVertriebId_findEinsaetzeBySuchkriterien" type=number step=1 name="mitarbeiterVertriebId"></td>
        </tr>
        <tr>
            <td><label for="mitarbeiterId_findEinsaetzeBySuchkriterien">Mitarbeiter (ID):</label></td>
            <td><input id="mitarbeiterId_findEinsaetzeBySuchkriterien" type=number step=1 name="mitarbeiterId"></td>
        </tr>
        <tr>
            <td><label for="mitarbeiterStatus">Status Mitarbeiter:</label></td>
            <td><select id="mitarbeiterStatus" name="mitarbeiterStatus">
                <option value=""></option>
                <option value="ANGESTELLT">ANGESTELLT</option>
                <option value="SUBUNTERNEHMER">SUBUNTERNEHMER</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="status">Status:</label></td>
            <td><select id="status" name="einsatzStatus">
                <option value=""></option>
                <option value="ANGEBOTEN">ANGEBOTEN</option>
                <option value="BEAUFTRAGT">BEAUFTRAGT</option>
                <option value="ABGELEHNT">ABGELEHNT</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="beginnVon">Beginn >=:</label></td>
            <td><input id="beginnVon" type=date name="beginnVon"></td>
        </tr>
        <tr>
            <td><label for="beginnBis">Beginn <=:</label></td>
            <td><input id="beginnBis" type=date name="beginnBis"></td>
        </tr>
        <tr>
            <td><label for="endeVon">Ende >=:</label></td>
            <td><input id="endeVon" type=date name="endeVon"></td>
        </tr>
        <tr>
            <td><label for="endeBis">Ende <=:</label></td>
            <td><input id="endeBis" type=date name="endeBis"></td>
        </tr>
        <tr>
            <td>
                <button>Liste der Eins&auml;tze nach Suchkriterien</button>
            </td>
        </tr>
    </table>
</form>
<hr/>
<form action="<%=request.getContextPath()%>/addEinsatz" method="POST" content="">
    <table class="editDataTable">
        <tr>
            <td><label for="mitarbeiterVertriebId_addEinsatz">Mitarbeiter Vertrieb (ID):</label></td>
            <td><input id="mitarbeiterVertriebId_addEinsatz" name="mitarbeiterVertriebId" value="1"></td>
        </tr>
        <tr>
            <td><label for="mitarbeiterId">Mitarbeiter (ID):</label></td>
            <td><input id="mitarbeiterId" name="mitarbeiterId" value="1"></td>
        </tr>
        <tr>
            <td><label for="einsatzStatus">Status:</label></td>
            <td><select id="einsatzStatus" name="einsatzStatus">
                <option value="ANGEBOTEN">ANGEBOTEN</option>
                <option value="BEAUFTRAGT">BEAUFTRAGT</option>
                <option value="ABGELEHNT">ABGELEHNT</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="beginn">Beginn:</label></td>
            <td><input id="beginn" type=date name="beginn" value="2020-01-01"></td>
        </tr>
        <tr>
            <td><label for="ende">Ende:</label></td>
            <td><input id="ende" type=date name="ende" value="2020-12-31"></td>
        </tr>
        <tr>
            <td><label for="wahrscheinlichkeit">Wahrscheinlichkeit (f&uuml;r Status angeboten):</label></td>
            <td><input id="wahrscheinlichkeit" type=number step=1 name="wahrscheinlichkeit" value="0"></td>
        </tr>
        <tr>
            <td><label for="zusatzkostenReise">Zusatzkosten Reise:</label></td>
            <td><input id="zusatzkostenReise" type=number step=0.01 name="zusatzkostenReise" value="0"></td>
        </tr>
        <tr>
            <td><label for="stundensatzVK">Stundensatz VK:</label></td>
            <td><input id="stundensatzVK" type=number step=0.01 name="stundensatzVK" value="0"></td>
        </tr>
        <tr>
            <td><label for="projektnummerNettitme">Projektnummer nettime:</label></td>
            <td><input id="projektnummerNettitme" name="projektnummerNettime"></td>
        </tr>
        <tr>
            <td><label for="beauftragungsnummer">Beauftragungsnummer:</label></td>
            <td><input id="beauftragungsnummer" name="beauftragungsnummer"></td>
        </tr>
        <tr>
            <td>
                <button>Einsatz Hinzuf&uuml;gen</button>
            </td>
        </tr>
    </table>
    <sec:csrfInput/>
</form>
<hr/>
<form action="<%=request.getContextPath()%>/deleteEinsatz" method="POST">
    <input title="Einsatz-Id" type=number step=1 name="einsatzId">
    <button>L&ouml;sche Einsatz (mit ID)</button>
    <sec:csrfInput/>
</form>
</body>
</html>