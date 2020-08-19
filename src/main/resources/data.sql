-- MITARBEITER
-- ID, MITARBEITER_STATUS, MITARBEITER_UNIT, NAME, STUNDENSATZEK, VORNAME
DELETE FROM Mitarbeiter;
insert into Mitarbeiter
values (1, 'ANGESTELLT','FACTORY_MUENCHEN', 'Mustermann', 30.0, 'Max');
insert into Mitarbeiter
values (2, 'SUBUNTERNEHMER', 'FACTORY_MUENCHEN', 'Stoteles', 50.0, 'Ari');
insert into Mitarbeiter
values (3, 'ANGESTELLT', 'FACTORY_MUENCHEN', 'Lühse', 36.0, 'Anna');
insert into Mitarbeiter
values (4, 'ANGESTELLT', 'FACTORY_NUERNBERG', 'Müller', 35.0, 'Werner');
insert into Mitarbeiter
values (5, 'SUBUNTERNEHMER', 'FACTORY_NUERNBERG', 'Schulz', 55.0, 'Renate');
insert into Mitarbeiter
values (6, 'SUBUNTERNEHMER', 'FACTORY_MUENCHEN', 'Schmidt', 60.0, 'Wolfgang');
insert into Mitarbeiter
values (7, 'ANGESTELLT', 'FACTORY_MUENCHEN', 'Lange', 29.5, 'Yvonne');

-- MITARBEITER_VERTRIEB
-- ID, NAME, VORNAME
DELETE FROM MITARBEITER_VERTRIEB;
insert into MITARBEITER_VERTRIEB
values (1, 'Günzkofer', 'Werner');
insert into MITARBEITER_VERTRIEB
values (2, 'Wüst', 'Jürgen');

-- EINSATZ
-- ID, BEAUFTRAGUNGSNUMMER, BEGINN, DECKUNGSBEITRAG, EINSATZ_STATUS, ENDE, MARGE, PROJEKTNUMMER_NETTIME, STUNDENSATZVK, WAHRSCHEINLICHKEIT, ZUSATZKOSTEN_REISE, MITARBEITER_ID, MITARBEITER_VERTRIEB_ID
DELETE FROM Einsatz;
insert into Einsatz
values (1, 'BeaufNr1', DATE '2020-09-01', 45.0, 'ANGEBOTEN', DATE '2020-12-31', 0.5, 'ProjektNr1', 90, 50, 15, 1, 1);
insert into Einsatz
values (2, 'BeaufNr2', DATE '2020-10-02', 25.0, 'BEAUFTRAGT', DATE '2021-03-31', 0.25, 'ProjektNr2', 100, 75, 25, 2, 2);
insert into Einsatz
values (3, 'BeaufNr3', DATE '2020-09-01', 47.5, 'ABGELEHNT', DATE '2021-12-31', 0.5, 'ProjektNr3', 95, 25, 11.5, 3, 1);
insert into Einsatz
values (4, 'BeaufNr4', DATE '2020-10-01', 28.0, 'BEAUFTRAGT', DATE '2020-12-31', 0.33, 'ProjektNr4', 84, 25, 20, 3, 1);
insert into Einsatz
values (5, 'BeaufNr5', DATE '2020-01-01', 50.0, 'BEAUFTRAGT', DATE '2020-08-31', 0.5, 'ProjektNr5', 100, 75, 15, 4, 2);
insert into Einsatz
values (6, 'BeaufNr6', DATE '2020-10-03', 16.5, 'ANGEBOTEN', DATE '2021-06-30', 0.2, 'ProjektNr6', 84, 25, 12.5, 5, 1);
insert into Einsatz
values (7, 'BeaufNr7', DATE '2020-10-03', 21.0, 'ANGEBOTEN', DATE '2021-06-30', 0.25, 'ProjektNr7', 84, 50, 8, 5, 2);
insert into Einsatz
values (8, 'BeaufNr8', DATE '2020-10-03', 18.5, 'BEAUFTRAGT', DATE '2021-01-30', 0.22, 'ProjektNr8', 84, 75, 10.5, 5, 2);