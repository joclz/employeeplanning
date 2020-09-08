**Backend:**

- Löschen des gebauten Backends 
mvn clean

- Compilieren der Backend-Sourcen
mvn compile

- Starten des Backends. Der Service ist über http://localhost:8080 erreichbar
mvn spring-boot:run

- Ausführen aller Unit Tests
mvn test

**Frontend:** (Im Ordner employeeplanning\angularclient)

Abhängigkeiten ziehen: npm install

Client starten: ng serve
Client starten (mit Browser): ng serve --open

**Datenbank:**

Datenbank zur Laufzeit: c:\temp\h2-db-employeeplanning.mv.db
Diese DB wird aktuell noch initial mit einigen Testdaten vorbelegt.

Zugangsdaten für die DB:
JDBC-URL: jdbc:h2:c:/temp/h2-db-employeeplanning
Username: sa
Password: 

Für die Unit-Tests wird aktuell eine Memory-DB herangezogen.