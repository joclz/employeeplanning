Backend:

- Löschen des gebauten Backends 
mvn clean

- Compilieren der Backend-Sourcen
mvn compile

- Starten des Backends. Der Service ist über http://localhost:8080 erreichbar
mvn spring-boot:run


Datenbank:

Datenbank zur Laufzeit: c:\temp\h2-db-employeeplanning.mv.db
Diese DB wird aktuell noch initial mit einigen Testdaten vorbelegt.

Für die Unit-Tests wird aktuell eine Memory-DB herangezogen.

