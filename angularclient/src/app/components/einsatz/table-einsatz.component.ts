import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzService} from "../../services/einsatz/einsatz.service";
import {UpdateEinsatzService} from "../../services/einsatz/update-einsatz.service";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {MatDialog} from "@angular/material/dialog";
import {DeleteEinsatzDialogComponent} from "./delete-einsatz-dialog.component";

@Component({
  selector: 'app-table-einsatz',
  templateUrl: './table-einsatz.component.html',
  styleUrls: ['./table-einsatz.component.css']
})
export class TableEinsatzComponent implements OnInit {

  displayedColumns: string[] = ['id', 'mitarbeiter', 'mitarbeiterVertrieb', 'einsatzStatus', 'beginn', 'ende',
    'wahrscheinlichkeit', 'zusatzkostenReise', 'stundensatzVK', 'projektnummerNettime', 'beauftragungsnummer',
    'deckungsbeitrag', 'marge', 'actions'];
  dataSource: MatTableDataSource<Einsatz>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  @Output() deleteEinsatzEvent = new EventEmitter();

  @Input() einsaetze: Einsatz[];

  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private einsatzService: EinsatzService,
              private updateEinsatzService: UpdateEinsatzService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.initDependingOnInput();
  }

  initDependingOnInput() {
    if (this.einsaetze) {
      this.init(this.einsaetze);
    } else {
      this.einsatzService.findAll().subscribe(result =>
        this.init(result));
    }
  }

  init(einsatz: Einsatz[]): void {
    this.dataSource = new MatTableDataSource(einsatz);

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  deleteEinsatz(id: string) {
    this.einsatzService.delete(id).subscribe(() => {
      this.initDependingOnInput();
      this.deleteEinsatzEvent.emit();
      this.showSuccessMsg = true;
      this.showErrorMsg = false;
    }, () => {
      this.showSuccessMsg = false;
      this.showErrorMsg = true;
    });
  }

  updateEinsatz(row: Einsatz) {
    this.updateEinsatzService.updateEinsatz(row);
  }

  getEinsatzStatus(status: EinsatzStatus): string {
    return EinsatzStatus[status];
  }

  openDeleteEinsatzDialog(id: string, name: string, vorname: string, einsatzStatus: string) {
    const dialogRef = this.dialog.open(DeleteEinsatzDialogComponent, {
      data: {
        id: id,
        name: name,
        vorname: vorname,
        einsatzStatus: einsatzStatus
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.deleteEinsatz(result)
      }
    });
  }
}
