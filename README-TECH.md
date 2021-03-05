**Backend:**

- Löschen des gebauten Backends:
mvn clean

- Compilieren der Backend-Sourcen:
mvn compile

- Starten des Backends. Der Service ist über http://localhost:8080 erreichbar:
mvn spring-boot:run

- Ausführen aller Unit Tests:
mvn test

- Erzeugen eines WAR-Files:
mvn clean package
  
  Hierbei wird ein war-File erzeugt, welches wie folgt gestartet werden kann:
  java -jar target/employeeplanning-0.0.1-SNAPSHOT.war
  
  Mit http://localhost:8080 ist die Angularoberfläche aufrufbar.
  Mit http://localhost:8080/start.html kann man noch die alte HTML-GUI aufrufen.

- Erzeugen eines produktiven WAR-Files:
mvn clean package -Pprod

Aktuell wird für den produktiven Stand ein anderer Port konfiguriert. Dies kann wie folgt getestet werden:
java -Dserver.port=9080 -jar target/employeeplanning-0.0.1-SNAPSHOT.war
###############################################  

**Frontend:** (Im Ordner employeeplanning\angularclient)

Abhängigkeiten ziehen: npm install

Client starten: ng serve
Client starten (mit Browser): ng serve --open
###############################################

**Datenbank:**

Datenbank zur Laufzeit: c:\temp\h2-db-employeeplanning.mv.db
Diese DB wird aktuell noch initial mit einigen Testdaten vorbelegt.

Zugangsdaten für die DB:
JDBC-URL: jdbc:h2:c:/temp/h2-db-employeeplanning
Username: sa
Password: 

Die Datenbank kann mit http://localhost:8080/h2-console betrachtet werden.

Für die Unit-Tests wird aktuell eine Memory-DB herangezogen.
###############################################

**Selenium**

Die Selenium-Tests sind aktuell auf Firefox ausgelegt.
Hierzu muss noch ein geckodriver installiert sein.
Auf diesen wird aktuell in SeleniumTest.java hardcoded zugegriffen:

- System.setProperty("webdriver.gecko.driver", "D:\\Programme\\geckodriver-v0.29.0-win64\\geckodriver.exe");

Die Selenium Tests werden aktuell nicht automatisch beim Build mit aufgerufen (junit.vintage ist excluded), sondern
müssen per Hand aufgerufen werden. Hierzu muss die Anwendung auch gestartet sein.
