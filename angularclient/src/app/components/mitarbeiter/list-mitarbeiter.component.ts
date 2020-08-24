import {Component, OnInit, ViewChild} from '@angular/core';
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {TableMitarbeiterComponent} from "./table-mitarbeiter.component";

@Component({
  selector: 'app-list-mitarbeiter',
  templateUrl: './list-mitarbeiter.component.html',
  styleUrls: ['./list-mitarbeiter.component.css']
})
export class ListMitarbeiterComponent implements OnInit {

  @ViewChild(TableMitarbeiterComponent) tableMitarbeiter: TableMitarbeiterComponent;

  constructor(private mitarbeiterService: MitarbeiterService) {
  }

  ngOnInit(): void {
    this.mitarbeiterService.findAll().subscribe(data => {
      this.tableMitarbeiter.init(data);
    });
  }

}
