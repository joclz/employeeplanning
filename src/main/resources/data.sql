-- MITARBEITER
-- ID, MITARBEITER_STATUS, MITARBEITER_UNIT, NAME, STUNDENSATZEK, VORNAME
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

-- MITARBEITER_VERTRIEB
-- ID, NAME, VORNAME
insert into MITARBEITER_VERTRIEB
values (1, 'Günzkofer', 'Werner');
insert into MITARBEITER_VERTRIEB
values (2, 'Wüst', 'Jürgen');

-- EINSATZ
-- ID, BEAUFTRAGUNGSNUMMER, BEGINN, DECKUNGSBEITRAG, EINSATZ_STATUS, ENDE, MARGE, PROJEKTNUMMER_NETTIME, STUNDENSATZVK, WAHRSCHEINLICHKEIT, ZUSATZKOSTEN_REISE, MITARBEITER_ID, MITARBEITER_VERTRIEB_ID
insert into Einsatz
values (1, 'BeaufNr1', DATE '2020-09-01', 10.0, 'ANGEBOTEN', DATE '2020-12-31', 11.0, 'ProjektNr1', 90, 50, 10, 1, 1);
insert into Einsatz
values (2, 'BeaufNr2', DATE '2020-10-02', 12.0, 'BEAUFTRAGT', DATE '2021-03-31', 13.0, 'ProjektNr2', 100, 75, 25, 2, 2);
insert into Einsatz
values (3, 'BeaufNr3', DATE '2020-09-01', 20.0, 'ABGELEHNT', DATE '2021-12-31', 12.0, 'ProjektNr3', 95, 25, 10, 3, 1);
insert into Einsatz
values (4, 'BeaufNr4', DATE '2020-10-01', 21.0, 'BEAUFTRAGT', DATE '2020-12-31', 13.0, 'ProjektNr4', 84, 25, 10, 3, 1);
insert into Einsatz
values (5, 'BeaufNr5', DATE '2020-01-01', 21.0, 'BEAUFTRAGT', DATE '2020-08-31', 14.0, 'ProjektNr5', 100, 75, 15, 4, 2);
insert into Einsatz
values (6, 'BeaufNr6', DATE '2020-10-03', 21.0, 'ANGEBOTEN', DATE '2021-06-30', 13.0, 'ProjektNr6', 84, 25, 10, 5, 1);