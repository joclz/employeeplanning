import {Component, Input, OnInit} from '@angular/core';
import {Einsatz} from "../../models/einsatz";

@Component({
  selector: 'app-update-einsatz',
  templateUrl: './update-einsatz.component.html',
  styleUrls: ['./update-einsatz.component.css']
})
export class UpdateEinsatzComponent implements OnInit {

  @Input() einsatzInput: Einsatz;

  constructor() { }

  ngOnInit(): void {
  }

}
