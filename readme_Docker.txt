zum Bauen eines Dockerfiles:
docker build --tag employeeplanning .

Zum Exportieren eines TAR-Files:
docker save employeeplanning > employeeplanning.tar

zum Hochkopieren eines Docker-Tar-Files auf cp-server-30:
pscp employeeplanning.tar matff@cp-server-30:/home/matff/employeeplanning.tar

Einspielen des Tar.Files in Docker:
docker load < employeeplanning.tar

Docker-Image sollte verf�gbar sein:
docker images

Docker Image starten:
docker run -p 8080:8080 employeeplanning
oder
docker run -p 80:8080 employeeplanning (um auf Default Port zu mappen)

Mit dem Parameter -d läuft das Image im Detached-Mode, d.h. das Image wird gestartet, die Console steht wieder zur Verfügung und kann geschlossen werden (sonst wird das Image gestoppt, wenn die Konsole geschlossen wird)


  Derzeitige Problematik mit der Datenbank (deprecated):
  Da die Datenbank derzeit im Dockerimage liegt und dort auch angezogen wird, wird die Datenbank bei jedem Beenden des Docker-Images wieder zur�ckgesetzt. Um die Daten in er Datenbank nicht zu verlieren, muss vor dem Herunterfahren des Docker-Images die Datenbank-Datei gesicehrt werden, und vor dem Aufruf des Java-Programms die Datenbank wieder eingespielt werden:
  - Zum Sichern der Datenbank folgenden Befehl eingeben (in einer zweiten Shell)
  docker cp d224a6c45073:/usr/local/employeeplanning/h2-db-employeeplanning.mv.db ./h2-db-employeeplanning.mv.db
  dabei ist die Nummer vor dem Doppelpunkt die Container ID (sichtbar �ber Auruf von "docker container ls"). Die Datenbank wird dabei in das aktuelle Verzeichnis auf dem cp-server-30 kopiert
  - Zum Einspielen der Datenbank sollte das Dockerimage nicht direkt mit dem mitgegebenen ENTRYPOINT aufgerufen werden, sondern mit
  docker run -p 80:8080  -it --entrypoint=/bin/bash   employeeplanning
  damit wird die Spring Boot-Anwendung nicht direkt gestartet. Jetzt kann die Datenbank mit dem umgekehrten Befehl 
  docker cp ./h2-db-employeeplanning.mv.db 0b0415e05d85:/usr/local/employeeplanning/h2-db-employeeplanning.mv.db
  in das laufende Docker-Image kopiert werden (Achtung, das Image hat eine neue Container-ID) und anschlie�end die Anwendung im Docker mit 
  java -jar /usr/local/employeeplanning/app.war
  gestartet werden,  und die zuvor gesicehrten Daten stehen wieder zur Verf�gung.


Aktuelle Situation mit der Datenbank:
Auf Produktion besfindet sich die H2-Datenbank nicht mehr im Dockerimage, sodnern wird in einem nicht vorhandenen Verzeichnis /usr/local/employeeplanning/db employeeplanning gesucht (Eintrag in application-prod.properties).
Beim Starten des Images, kann über den Volume-Befehl -v der Pfad auf eine außerhalb des Images liegende Datenbank verlinkt werden. Derzeit liegt eine Datenbank auf cp-server-30 unter /home/matff. Also z.B. über: 
docker run -p 80:8080 -d -v /home/matff:/usr/local/employeeplanning/db employeeplanning