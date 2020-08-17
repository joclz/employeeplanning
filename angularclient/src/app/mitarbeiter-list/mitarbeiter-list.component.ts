import {Component, OnInit} from '@angular/core';
import {Mitarbeiter} from "../mitarbeiter";
import {MitarbeiterService} from "../mitarbeiter.service";

@Component({
  selector: 'app-mitarbeiter-list',
  templateUrl: './mitarbeiter-list.component.html',
  styleUrls: ['./mitarbeiter-list.component.css']
})
export class MitarbeiterListComponent implements OnInit {

  mitarbeiter: Mitarbeiter[];

  constructor(private mitarbeiterService: MitarbeiterService) { }

  ngOnInit(): void {
    this.mitarbeiterService.findAll().subscribe(data => {
      this.mitarbeiter = data;
    })
  }

}
