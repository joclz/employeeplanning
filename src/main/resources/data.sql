--ID, MITARBEITER_STATUS, MITARBEITER_UNIT, NAME, STUNDENSATZEK, VORNAME
insert into Mitarbeiter
values (1, 1, 1, 'Mustermann', 30.0, 'Max');
insert into Mitarbeiter
values (2, 2, 1, 'Mayer', 50.0, 'Hans');
insert into Mitarbeiter
values (3, 1, 2, 'Müller', 35.0, 'Werner');
insert into Mitarbeiter
values (4, 2, 2, 'Schulz', 55.0, 'Renate');

--ID, BEAUFTRAGUNGSNUMMER, BEGINN, DECKUNGSBEITRAG, EINSATZ_STATUS, ENDE, MARGE, PROJEKTNUMMER_NETTIME, STUNDENSATZVK, WAHRSCHEINLICHKEIT, ZUSATZKOSTEN_REISE, MITARBEITER_ID
insert into Einsatz
values (1, 'BeaufNr1', DATE '2020-09-01', 10.0, 1, DATE '2020-12-31', 11.0, 'ProjektNr1', 26, 50, 35, 1);