CREATE TABLE mitarbeiter
(
   ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
   MITARBEITER_STATUS varchar(255),
   MITARBEITER_UNIT varchar(255),
   NAME varchar(255),
   STUNDENSATZEK double NOT NULL,
   VORNAME varchar(255)
)
;
CREATE UNIQUE INDEX PRIMARY_KEY_D ON mitarbeiter(ID)
;


INSERT INTO mitarbeiter (ID,MITARBEITER_STATUS,MITARBEITER_UNIT,NAME,STUNDENSATZEK,VORNAME) VALUES (1,'ANGESTELLT','FACTORY_MUENCHEN','Mustermann',30.0,'Max');
INSERT INTO mitarbeiter (ID,MITARBEITER_STATUS,MITARBEITER_UNIT,NAME,STUNDENSATZEK,VORNAME) VALUES (2,'SUBUNTERNEHMER','FACTORY_MUENCHEN','Stoteles',50.0,'Ari');
INSERT INTO mitarbeiter (ID,MITARBEITER_STATUS,MITARBEITER_UNIT,NAME,STUNDENSATZEK,VORNAME) VALUES (4,'ANGESTELLT','FACTORY_NUERNBERG','Müller',35.0,'Werner');
INSERT INTO mitarbeiter (ID,MITARBEITER_STATUS,MITARBEITER_UNIT,NAME,STUNDENSATZEK,VORNAME) VALUES (10,'ANGESTELLT','FACTORY_MUENCHEN','Huber',15.0,'Hans');
INSERT INTO mitarbeiter (ID,MITARBEITER_STATUS,MITARBEITER_UNIT,NAME,STUNDENSATZEK,VORNAME) VALUES (11,'ANGESTELLT','FACTORY_MUENCHEN','Müller',12.0,'Lisa');


CREATE TABLE mitarbeiter_vertrieb
(
   ID integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
   NAME varchar(255),
   VORNAME varchar(255)
)
;
CREATE UNIQUE INDEX PRIMARY_KEY_4 ON mitarbeiter_vertrieb(ID)
;


INSERT INTO mitarbeiter_vertrieb (ID,NAME,VORNAME) VALUES (1,'Günzkofer','Werner');
INSERT INTO mitarbeiter_vertrieb (ID,NAME,VORNAME) VALUES (2,'Wüst','Jürgen');
CREATE TABLE einsatz
(
   ID integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
   BEAUFTRAGUNGSNUMMER varchar(255),
   BEGINN timestamp,
   DECKUNGSBEITRAG double NOT NULL,
   EINSATZ_STATUS varchar(255),
   ENDE timestamp,
   MARGE double NOT NULL,
   PROJEKTNUMMER_NETTIME varchar(255),
   STUNDENSATZVK double NOT NULL,
   WAHRSCHEINLICHKEIT integer NOT NULL,
   ZUSATZKOSTEN_REISE double NOT NULL,
   MITARBEITER_ID integer,
   MITARBEITER_VERTRIEB_ID integer
)
;
ALTER TABLE einsatz
ADD CONSTRAINT FKLPR8GQNPXMOWQ13S1VIIP63Y5
FOREIGN KEY (MITARBEITER_VERTRIEB_ID)
REFERENCES mitarbeiter_vertrieb(ID) ON DELETE CASCADE
;
ALTER TABLE einsatz
ADD CONSTRAINT FKTN7N733S0D4O71E5LXM45YW6U
FOREIGN KEY (MITARBEITER_ID)
REFERENCES mitarbeiter(ID) ON DELETE CASCADE
;
CREATE INDEX FKTN7N733S0D4O71E5LXM45YW6U_INDEX_C ON einsatz(MITARBEITER_ID)
;
CREATE INDEX FKLPR8GQNPXMOWQ13S1VIIP63Y5_INDEX_C ON einsatz(MITARBEITER_VERTRIEB_ID)
;
CREATE UNIQUE INDEX PRIMARY_KEY_C ON einsatz(ID)
;


INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (1,'',{ts '2020-01-01 00:00:00.0'},-30.0,'ANGEBOTEN',{ts '2020-12-31 00:00:00.0'},0.0,'',0.0,0,0.0,1,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (2,'BeaufNr2',{ts '2020-10-02 00:00:00.0'},25.0,'BEAUFTRAGT',{ts '2021-03-31 00:00:00.0'},0.25,'ProjektNr2',100.0,75,25.0,2,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (5,'BeaufNr5',{ts '2020-01-01 00:00:00.0'},50.0,'BEAUFTRAGT',{ts '2020-08-31 00:00:00.0'},0.5,'ProjektNr5',100.0,75,15.0,4,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (21,'',{ts '2020-01-01 00:00:00.0'},-30.0,'ANGEBOTEN',{ts '2020-12-31 00:00:00.0'},0.0,'',0.0,0,0.0,1,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (22,'',{ts '2020-01-01 00:00:00.0'},-30.0,'ANGEBOTEN',{ts '2020-12-31 00:00:00.0'},0.0,'',0.0,0,0.0,1,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (54,'',{ts '2020-01-01 00:00:00.0'},-30.0,'ANGEBOTEN',{ts '2020-12-31 00:00:00.0'},0.0,'',0.0,0,0.0,1,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (86,'',{ts '2020-01-01 00:00:00.0'},-30.0,'ANGEBOTEN',{ts '2020-12-31 00:00:00.0'},0.0,'',0.0,0,0.0,1,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (87,'50',{ts '2020-09-09 00:00:00.0'},-50.0,'ANGEBOTEN',{ts '2020-09-10 00:00:00.0'},-1.0,'50',50.0,50,50.0,2,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (119,'50',{ts '2020-09-08 00:00:00.0'},-50.0,'BEAUFTRAGT',{ts '2020-09-09 00:00:00.0'},-1.0,'50',50.0,50,50.0,2,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (120,'50',{ts '2020-09-09 00:00:00.0'},-50.0,'BEAUFTRAGT',{ts '2020-09-10 00:00:00.0'},-1.0,'50',50.0,50,50.0,2,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (153,'75',{ts '2020-09-09 00:00:00.0'},35.0,'BEAUFTRAGT',{ts '2020-09-10 00:00:00.0'},0.4666666666666667,'75',75.0,75,5.0,4,2);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (154,'4',{ts '2020-09-16 00:00:00.0'},-50.0,'ANGEBOTEN',{ts '2020-09-18 00:00:00.0'},-12.5,'4',4.0,4,4.0,2,1);
INSERT INTO einsatz (ID,BEAUFTRAGUNGSNUMMER,BEGINN,DECKUNGSBEITRAG,EINSATZ_STATUS,ENDE,MARGE,PROJEKTNUMMER_NETTIME,STUNDENSATZVK,WAHRSCHEINLICHKEIT,ZUSATZKOSTEN_REISE,MITARBEITER_ID,MITARBEITER_VERTRIEB_ID) VALUES (155,'2',{ts '2020-09-01 00:00:00.0'},-12.0,'ABGELEHNT',{ts '2020-09-06 00:00:00.0'},-6.0,'2',2.0,2,2.0,11,1);

commit;