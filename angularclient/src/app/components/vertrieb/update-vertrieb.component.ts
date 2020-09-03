import {Component, Input, OnInit} from '@angular/core';
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";

@Component({
  selector: 'app-update-vertrieb',
  templateUrl: './update-vertrieb.component.html',
  styleUrls: ['./update-vertrieb.component.css']
})
export class UpdateVertriebComponent implements OnInit {

  @Input() mitarbeiterVertriebInput: MitarbeiterVertrieb;

  constructor() { }

  ngOnInit(): void {
  }

}
