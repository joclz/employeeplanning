import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {UpdateMitarbeiterService} from "../../services/mitarbeiter/update-mitarbeiter.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteMitarbeiterDialogComponent} from "./delete-mitarbeiter-dialog.component";

@Component({
  selector: 'app-table-mitarbeiter',
  templateUrl: './table-mitarbeiter.component.html',
  styleUrls: ['./table-mitarbeiter.component.css']
})
export class TableMitarbeiterComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'vorname', 'mitarbeiterStatus', 'stundensatzEK', 'mitarbeiterUnit', 'actions'];
  dataSource: MatTableDataSource<Mitarbeiter>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  @Input() isMitarbeiterBank: boolean;
  @Input() isMitarbeiterInternBank: boolean;

  @Output() deleteMitarbeiterEvent = new EventEmitter();

  constructor(private mitarbeiterService: MitarbeiterService,
              private updateMitarbeiterService: UpdateMitarbeiterService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.initDependingOnInput();
  }

  initDependingOnInput() {
    if (this.isMitarbeiterBank) {
      this.mitarbeiterService.getMitarbeiterBank().subscribe(result =>
        this.init(result));
    }
    if (this.isMitarbeiterInternBank) {
      this.mitarbeiterService.getMitarbeiterInternBank().subscribe(result =>
        this.init(result));
    }
    if (!this.isMitarbeiterBank && !this.isMitarbeiterInternBank) {
      this.mitarbeiterService.findAll().subscribe(result =>
        this.init(result));
    }
  }

  init(mitarbeiter: Mitarbeiter[]): void {
    this.dataSource = new MatTableDataSource(mitarbeiter);

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

  deleteMitarbeiter(id: string) {
    this.mitarbeiterService.delete(id).subscribe(() => {
      this.initDependingOnInput();
      this.deleteMitarbeiterEvent.emit();
    });
  }

  updateMitarbeiter(row: Mitarbeiter) {
    this.updateMitarbeiterService.updateMitarbeiter(row);
  }

  openDeleteEinsatzDialog(id: string, name: string, vorname: string) {
    const dialogRef = this.dialog.open(DeleteMitarbeiterDialogComponent, {
      data: {
        id: id,
        name: name,
        vorname: vorname
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteMitarbeiter(result)
      }
    });
  }

  //TODO Enums übersetzen
}
