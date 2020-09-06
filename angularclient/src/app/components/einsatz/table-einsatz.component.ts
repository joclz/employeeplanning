import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Einsatz} from "../../models/einsatz";
import {EinsatzService} from "../../services/einsatz.service";
import {UpdateEinsatzService} from "../../services/update-einsatz.service";
import {EinsatzStatus} from "../../models/einsatz-status.enum";
import {MitarbeiterStatus} from "../../models/mitarbeiter-status.enum";

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

  einsatzStaus: EinsatzStatus;
  einsatzStatusList: any;

  mitarbeiterStatus = MitarbeiterStatus;
  mitarbeiterStatusList = [];

  constructor(private einsatzService: EinsatzService, private updateEinsatzService: UpdateEinsatzService) {
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatus);
    // this.einsatzStatusList = Object.keys(this.einsatzStaus);
  }

  ngOnInit() {
    this.initDependingOnInput();
  }

  initDependingOnInput() {
    this.einsatzService.findAll().subscribe(result =>
      this.init(result));
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
    });
  }

  updateEinsatz(row: Einsatz) {
    this.updateEinsatzService.updateEinsatz(row);
  }

  getEinsatzStatus(status: EinsatzStatus): string{
    return EinsatzStatus[status];
  }

}
