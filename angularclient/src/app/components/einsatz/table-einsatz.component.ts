import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzService} from "../../services/einsatz/einsatz.service";
import {UpdateEinsatzService} from "../../services/einsatz/update-einsatz.service";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {MatDialog} from "@angular/material/dialog";
import {DeleteEinsatzDialogComponent} from "./delete-einsatz-dialog.component";
import {HttpClient, HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-table-einsatz',
  templateUrl: './table-einsatz.component.html',
  styleUrls: ['./table-einsatz.component.css']
})
export class TableEinsatzComponent /* implements OnInit */ {

  displayedColumns: string[] = ['id', 'mitarbeiter', 'mitarbeiterVertrieb', 'einsatzStatus', 'beginn', 'ende',
    'wahrscheinlichkeit', 'zusatzkostenReise', 'stundensatzVK', 'projektnummerNettime', 'beauftragungsnummer',
    'deckungsbeitrag', 'marge', 'actions'];
    
  dataSource = new MatTableDataSource<Einsatz>();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  @Output() deleteEinsatzEvent = new EventEmitter();

  @Input() einsaetze: Einsatz[];
  
  loading: boolean = true;
  
  pageEvent: PageEvent;
  
  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private einsatzService: EinsatzService,
              private updateEinsatzService: UpdateEinsatzService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.getData('0', '5');
//    this.initDependingOnInput();
  }
  
//  initDependingOnInput() {
//    if (this.einsaetze) {
//      this.init(this.einsaetze);
//    } else {
//      this.einsatzService.findAll().subscribe(result =>
//        this.init(result));
//    }
//  }

//  init(einsatz: Einsatz[]): void {
//    this.dataSource = new MatTableDataSource(einsatz);

//    this.dataSource.paginator = this.paginator;
//    this.dataSource.sort = this.sort;
//  }

  getData(offset, limit){
    let params = new HttpParams();
    params = params.set('page', offset);
    params = params.set('size', limit);

    this.einsatzService.getPartialEinsaetze(params).subscribe((response: any) => {
      this.loading = false;
      this.einsaetze = response.einsaetze;
      this.einsaetze.length = response.anzahl;

      this.dataSource = new MatTableDataSource<any>(this.einsaetze);
      this.dataSource.paginator = this.paginator;
    })
  }

  getNextData(currentSize, offset, limit){
    let params = new HttpParams();
    params = params.set('page', offset);
    params = params.set('size', limit);

    this.einsatzService.getPartialEinsaetze(params).subscribe((response: any) => {
      this.loading = false;

      this.einsaetze.length = currentSize;
      this.einsaetze.push(...response.einsaetze);

      this.einsaetze.length = response.anzahl;

      this.dataSource = new MatTableDataSource<any>(this.einsaetze);
      this.dataSource._updateChangeSubscription();

      this.dataSource.paginator = this.paginator;
  
    })
  }

  public getPaginatorData(event?:PageEvent) {
    this.loading = true;

    let pageIndex = event.pageIndex;
    let pageSize = event.pageSize;

    let previousIndex = event.previousPageIndex;

    let previousSize = pageSize * pageIndex;

    this.getNextData(previousSize, (pageIndex).toString(), pageSize.toString());
    
    return event;
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
//      this.initDependingOnInput();
      this.deleteEinsatzEvent.emit();
      
      this.paginator.pageIndex = 0
      this.getNextData(0, 0, this.paginator.pageSize);
      
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
