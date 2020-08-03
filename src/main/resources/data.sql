--ID, MITARBEITER_STATUS, MITARBEITER_UNIT, NAME, STUNDENSATZEK, VORNAME
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

--ID, BEAUFTRAGUNGSNUMMER, BEGINN, DECKUNGSBEITRAG, EINSATZ_STATUS, ENDE, MARGE, PROJEKTNUMMER_NETTIME, STUNDENSATZVK, WAHRSCHEINLICHKEIT, ZUSATZKOSTEN_REISE, MITARBEITER_ID
insert into Einsatz
values (1, 'BeaufNr1', DATE '2020-09-01', 10.0, 'ANGEBOTEN', DATE '2020-12-31', 11.0, 'ProjektNr1', 26, 50, 35, 1);
insert into Einsatz
values (2, 'BeaufNr2', DATE '2020-10-02', 12.0, 'ANGEBOTEN', DATE '2021-03-31', 13.0, 'ProjektNr2', 27, 75, 36, 2);