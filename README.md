# employeeplanning
Spring / Spring Boot Training Projekt zur Abbildung des Prozesses für Aufträge im PS-Geschäft.

In einer Einfachen Version gibt es 2 Entitäten:

Mitarbeiter (Attribute):
o	Angestellt / Subunternehmer
o	Stundensatz EK
o	Name, Vorname
o	Unit (z.B. Factory München) 

Einsatz:
•	Zu einem Einsatz gibt es genau einen Mitarbeiter, ein Mitarbeiter kann mehrere Einsätze haben
•	Ein Mitarbeiter kann gleichzeitig mehrere Einsätze haben (mindestens im Status angeboten)
•	Attribute (erfasst durch Vertrieb):
o	Status (angeboten/beauftragt/abgelehnt)
o	Beginn (Datum)
o	Ende (Datum)
o	Wahrscheinlichkeit (für status angeboten)
o	Zusatzkosten Reise (als Stundensatz)
o	Stundensatz VK
o	Projektnummer nettime (erfasst durch Auftragsverwaltung)
o	Beauftragungsnummer (erfasst durch Auftragsverwaltung)
•	Berechnete Atribute:
o	Deckungsbeitrag  (pro Stunde und hochgerechnet pro Monat)
o	Marge in % (= Deckungsbeitrag/VK)

Prozesse:
•	Vertrieb erfasst Einsätze (ggf. später auch Kunde/Projekt) und kann diese auch Pflegen z.B. also Statusänderung von angeboten zu beauftragt (nur eigene Projekte)
•	Vertrieb hat eine einfache Möglichkeit weitere geplante Einsätze zu hinterlegen (beauftragt ist 1 Quartal – wir gehen aber von einem Einsatz bis Jahresende aus mit einer Wahrscheinlichkeit von 90% – z.B. weitere 9 Monate) 
•	Vertrieb hat eine einfache Möglichkeit Projektverlängerungen zu erfassen (es werden wieder 3 Monate beauftragt, dabei ändert sich der VK – die restliche Planungsperiode bleibt bestehen.)
•	Delivery (alle internen Mitarbeiter der eigenen Unit)
o	Wie lange sind die Mitarbeiter noch im Einsatz
o	Wie hoch ist die Chance für Verlängerung
o	Welche Alternativen für neue Einsätze gibt es
o	Welche Mitarbeiter sitzen auf der „Bank“
o	Welche Chancen für Mitarbeiter auf der „Bank“ gibt es und über welchen Vertriebsmitarbeiter
•	Geschäftsleitung nutzt das System, um den aktuellen Status (z.B. auf einem Dashboard zu sehen)
o	Wieviele Mitarbeiter bzw. Subunternehmer sind im Einsatz (aktueller Monat bzw. im Jahresverlauf)
o	Welche internen Mitarbeiter sind aktuell nicht im Einsatz
o	Wo bzw. wie oft sind interne Mitarbeiter aktuell angeboten
o	Welchen Umsatz bzw. Deckungsbeitrag haben wir (aktueller Monat bzw. im Jahresverlauf)

In einer Erweiterung könnte es noch weitere Entitäten geben:
•	Abrechnungen:
o	Ein Einsatz hat pro Monat maximal eine Eingangs und eine Ausgangsrechnung (Datum, Rechnungsnummer, ggf. auch Betrag)
•	Kunde/Projekt
o	Ein Kunde/Projekt hat 1-n Einsätze und enthält Attribute, die Übergreifend für Einsätze sind (z.B. Kundenname, Ansprechpartner beim Kunden, Rechnungsadresse)
•	EK 
o	Ein Mitarbeiter kann unterschiedliche EK’s über die Zeit haben (z.B. durch Gehaltserhöhungen)
