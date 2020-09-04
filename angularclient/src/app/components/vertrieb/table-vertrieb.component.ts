import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MitarbeiterVertriebService} from "../../services/mitarbeiter-vertrieb.service";
import {UpdateMitarbeiterVertriebService} from "../../services/update-mitarbeiter-vertrieb.service";

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

  constructor(private mitarbeiterVertriebService: MitarbeiterVertriebService, private updateMitarbeiterVertriebService: UpdateMitarbeiterVertriebService) {
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
    });
  }

  updateMitarbeiterVertrieb(row: MitarbeiterVertrieb) {
    this.updateMitarbeiterVertriebService.updateMitarbeiterVertrieb(row);
  }

}
