import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MitarbeiterVertrieb} from "../../models/vertrieb/mitarbeiter-vertrieb";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MitarbeiterVertriebService} from "../../services/vertrieb/mitarbeiter-vertrieb.service";
import {UpdateMitarbeiterVertriebService} from "../../services/vertrieb/update-mitarbeiter-vertrieb.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteVertriebDialogComponent} from "./delete-vertrieb-dialog.component";

@Component({
  selector: 'app-table-vertrieb',
  templateUrl: './table-vertrieb.component.html',
  styleUrls: ['./table-vertrieb.component.css']
})
export class TableVertriebComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'vorname', 'actions'];
  dataSource: MatTableDataSource<MitarbeiterVertrieb>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  @Output() deleteMitarbeiterVertriebEvent = new EventEmitter();

  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private mitarbeiterVertriebService: MitarbeiterVertriebService,
              private updateMitarbeiterVertriebService: UpdateMitarbeiterVertriebService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.mitarbeiterVertriebService.findAll().subscribe(result =>
      this.init(result));
  }

  init(mitarbeiterVertrieb: MitarbeiterVertrieb[]): void {
    this.dataSource = new MatTableDataSource(mitarbeiterVertrieb);

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

  deleteMitarbeiterVertrieb(id: string) {
    this.mitarbeiterVertriebService.delete(id).subscribe(() => {
      this.ngOnInit();
      this.deleteMitarbeiterVertriebEvent.emit();
      this.showSuccessMsg = true;
      this.showErrorMsg = false;
    }, () => {
      this.showSuccessMsg = false;
      this.showErrorMsg = true;
    });
  }

  updateMitarbeiterVertrieb(row: MitarbeiterVertrieb) {
    this.updateMitarbeiterVertriebService.updateMitarbeiterVertrieb(row);
  }

  openDeleteEinsatzDialog(id: string, name: string, vorname: string) {
    const dialogRef = this.dialog.open(DeleteVertriebDialogComponent, {
      data: {
        id: id,
        name: name,
        vorname: vorname
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteMitarbeiterVertrieb(result)
      }
    });
  }

}
