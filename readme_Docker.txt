zum Hochkopieren eines Docker-Tar-Files auf cp-server-30:
pscp employeeplanning.tar matff@cp-server-30:/home/matff/employeeplanning.tar

Einspielen des Tar.Files in Docker:
docker load < employeeplanning.tar

Docker-Image sollte verfügbar sein:
docker images

Docker Image starten:
docker run -p 8080:8080 employeeplanning
oder
docker run -p 80:8080 employeeplanning (um auf Default Port zu mappen)


Derzeitige Problematik mit der Datenbank:
Da die Datenbank derzeit im Dockerimage liegt und dort auch angezogen wird, wird die Datenbank bei jedem Beenden des Docker-Images wieder zurückgesetzt. Um die Daten in er Datenbank nicht zu verlieren, muss vor dem Herunterfahren des Docker-Images die Datenbank-Datei gesicehrt werden, und vor dem Aufruf des Java-Programms die Datenbank wieder eingespielt werden:
- Zum Sichern der Datenbank folgenden Befehl eingeben (in einer zweiten Shell)
docker cp d224a6c45073:/usr/local/employeeplanning/h2-db-employeeplanning.mv.db ./h2-db-employeeplanning.mv.db
dabei ist die Nummer vor dem Doppelpunkt die Container ID (sichtbar über Auruf von "docker container ls"). Die Datenbank wird dabei in das aktuelle Verzeichnis auf dem cp-server-30 kopiert
- Zum Einspielen der Datenbank sollte das Dockerimage nicht direkt mit dem mitgegebenen ENTRYPOINT aufgerufen werden, sondern mit
docker run -p 80:8080  -it --entrypoint=/bin/bash   employeeplanning
damit wird die Spring Boot-Anwendung nicht direkt gestartet. Jetzt kann die Datenbank mit dem umgekehrten Befehl 
docker cp ./h2-db-employeeplanning.mv.db 0b0415e05d85:/usr/local/employeeplanning/h2-db-employeeplanning.mv.db
in das laufende Docker-Image kopiert werden (Achtung, das Image hat eine neue Container-ID) und anschließend die Anwendung im Docker mit 
java -jar /usr/local/employeeplanning/app.war
gestartet werden,  und die zuvor gesicehrten Daten stehen wieder zur Verfügung.

